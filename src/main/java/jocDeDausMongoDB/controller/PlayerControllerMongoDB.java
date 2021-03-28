package jocDeDausMongoDB.controller;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.GameCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import jocDeDausMongoDB.collection.Ranking;
import jocDeDausMongoDB.controller.exception.PlayerNotFoundException;
import jocDeDausMongoDB.dto.CrapsRollModel;
import jocDeDausMongoDB.dto.PlayerModel;
import jocDeDausMongoDB.service.ICrapsRollService;
import jocDeDausMongoDB.service.IGameService;
import jocDeDausMongoDB.service.IPlayerService;
import jocDeDausMongoDB.util.IUtilities;
import jocDeDausMongoDB.util.assembler.CrapsRollModelAssembler;
import jocDeDausMongoDB.util.assembler.PlayerModelAssembler;
import jocDeDausMongoDB.util.assembler.RankingModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * Clase de la capa Controller
 *
 * Punto de acceso a las peticiones sera http://localhost:8082/{path},
 *
 */
@RepositoryRestController
public class PlayerControllerMongoDB {


    private final IUtilities iUtilities;
    private final IPlayerService iPlayerService;
    private final ICrapsRollService iCrapsRollService;
    private final IGameService iGameService;
    private final PagedResourcesAssembler<PlayerCollection> playerPagedResourcesAssembler;
    private final PagedResourcesAssembler<CrapsRollCollection> crapsRollPagedResourcesAssembler;
    private final PlayerModelAssembler playerModelAssembler;
    private final CrapsRollModelAssembler crapsRollModelAssembler;
    private final RankingModelAssembler rankingModelAssembler;

    /**
     * Constructor de la clase
     *
     * @param iUtilities
     * @param iPlayerService
     * @param iCrapsRollService
     * @param iGameService
     * @param playerPagedResourcesAssembler
     * @param crapsRollPagedResourcesAssembler
     * @param playerModelAssembler
     * @param crapsRollModelAssembler
     * @param rankingModelAssembler
     */
    @Autowired
    public PlayerControllerMongoDB(IUtilities iUtilities, IPlayerService iPlayerService,
                                   ICrapsRollService iCrapsRollService, IGameService iGameService,
                                   PagedResourcesAssembler<PlayerCollection> playerPagedResourcesAssembler,
                                   PagedResourcesAssembler<CrapsRollCollection> crapsRollPagedResourcesAssembler, PlayerModelAssembler playerModelAssembler,
                                   CrapsRollModelAssembler crapsRollModelAssembler,
                                   RankingModelAssembler rankingModelAssembler) {
        this.iUtilities = iUtilities;
        this.iPlayerService = iPlayerService;
        this.iCrapsRollService = iCrapsRollService;
        this.iGameService = iGameService;
        this.playerPagedResourcesAssembler = playerPagedResourcesAssembler;
        this.crapsRollPagedResourcesAssembler = crapsRollPagedResourcesAssembler;
        this.playerModelAssembler = playerModelAssembler;
        this.crapsRollModelAssembler = crapsRollModelAssembler;
        this.rankingModelAssembler = rankingModelAssembler;
    }

    /**
     * Mapeo de una peticion HTTP POST, a la URL http://localhost:8082/players
     *
     * Crea un jugador
     *
     * @param newPlayerCollection
     * @return
     */
    @PostMapping("/players")
    public ResponseEntity<?> newPlayer(@Valid @RequestBody PlayerCollection newPlayerCollection) {
        List<PlayerCollection> playerCollections = iPlayerService.listPlayers();
        Optional<PlayerCollection> uniqueNickName = IUtilities.checkUniqueNickName(
                newPlayerCollection, playerCollections);

        if (!uniqueNickName.isPresent()){
            PlayerCollection playerCollection = iPlayerService.savePlayer(newPlayerCollection);

            return new ResponseEntity<>(
                    playerModelAssembler.toModel(playerCollection),
                    HttpStatus.OK);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Problem.create()
                        .withTitle("Please select another Nick Name.")
                        .withDetail("There is another player with that Nick Name."));
    }

    /**
     * Mapeo de una peticion HTTP PUT, a la URL http://localhost:8082/players/{id}
     *
     * Modifica el nombre de un jugador
     *
     * @param newPlayerCollection
     * @param idPlayer
     * @return
     */
    @PutMapping("/players/{id}")
    public ResponseEntity<?> updatePlayer(@Valid @RequestBody PlayerCollection newPlayerCollection,
                                          @PathVariable(name="id") String idPlayer) {
        List<PlayerCollection> playerCollections = iPlayerService.listPlayers();

        Optional<PlayerCollection> uniqueNickName = IUtilities.checkUniqueNickName(
                newPlayerCollection, playerCollections);
        if (!uniqueNickName.isPresent()){
            PlayerCollection updatedPlayerCollection = iPlayerService.findPlayerById(idPlayer)
                    .map(player -> {
                        player.setName(newPlayerCollection.getName().trim());

                        player.setRanking(
                                IUtilities.computePlayerSuccessRanking(
                                        iCrapsRollService.listCrapsRollsByIdPlayer(player.getIdPlayer())));

                        player.setCrapsRolls(iCrapsRollService.listCrapsRollsByIdPlayer(idPlayer));

                        return iPlayerService.savePlayer(player);
                    })
                    .orElseGet(() -> {
                        newPlayerCollection.setIdPlayer(idPlayer);

                        newPlayerCollection.setRanking(
                                IUtilities.computePlayerSuccessRanking(
                                        iCrapsRollService.listCrapsRollsByIdPlayer(idPlayer)));

                        newPlayerCollection.setCrapsRolls(iCrapsRollService.listCrapsRollsByIdPlayer(idPlayer));

                        return iPlayerService.savePlayer(newPlayerCollection);
                    });
            return new ResponseEntity<>(
                    playerModelAssembler.toModel(updatedPlayerCollection),
                    HttpStatus.OK);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Problem.create()
                        .withTitle("Please select another Nick Name.")
                        .withDetail("There is another player with that Nick Name."));
    }

    /**
     * Mapeo de una peticion HTTP GET, a la URL http://localhost:8082/players/{id}
     *
     * Get one player
     *
     * @param idPlayer
     * @return
     */
    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerModel> one(@PathVariable(name="id") String idPlayer) {
        PlayerCollection player = iPlayerService.findPlayerById(idPlayer)
                .orElseThrow(() -> new PlayerNotFoundException(idPlayer));
        player.setRanking(
                IUtilities.computePlayerSuccessRanking(
                        iCrapsRollService.listCrapsRollsByIdPlayer(player.getIdPlayer())));

        player.setCrapsRolls(iCrapsRollService.listCrapsRollsByIdPlayer(idPlayer));

        return new ResponseEntity<>(
                playerModelAssembler.toModel(player),
                HttpStatus.OK);
    }

    /**
     * Mapeo de una peticion HTTP DELETE, a la URL http://localhost:8082/players/{id}
     *
     * Delete one player
     *
     * @param idPlayer
     * @return
     */
    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable(name="id") String idPlayer) {
        PlayerCollection player = iPlayerService.findPlayerById(idPlayer)
                .orElseThrow(() -> new PlayerNotFoundException(idPlayer));

        iPlayerService.deletePlayer(player);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mapeo de una peticion HTTP GET, a la URL http://localhost:8082/players
     *
     * Listado de jugadores junto con su porcentaje medio de exito
     *
     * @param pageable
     * @return
     */
    @GetMapping("/players")
    public ResponseEntity<PagedModel<PlayerModel>> allPlayers(Pageable pageable){
        Page<PlayerCollection> playerCollections = iPlayerService.listPlayers(pageable);

        playerCollections.forEach(p -> p.setRanking(
                IUtilities.computePlayerSuccessRanking(
                        iCrapsRollService.listCrapsRollsByIdPlayer(p.getIdPlayer()))));

        playerCollections.forEach(p -> p.setCrapsRolls(
                iCrapsRollService.listCrapsRollsByIdPlayer(p.getIdPlayer())));

        PagedModel<PlayerModel> collectionModel = playerPagedResourcesAssembler
                .toModel(playerCollections, playerModelAssembler);

        return new ResponseEntity<>(collectionModel,HttpStatus.OK);
    }

    /**
     * Mapeo de una peticion HTTP GET, a la URL http://localhost:8082/players/list
     *
     * Listado de jugadores junto con su porcentaje medio de exito
     *
     * @return
     */
    @GetMapping("/players/list")
    public ResponseEntity<CollectionModel<PlayerModel>> allPlayers()
    {
        List<PlayerCollection> playerCollections = iPlayerService.listPlayers();

        playerCollections.forEach(p -> p.setRanking(
                IUtilities.computePlayerSuccessRanking(
                        iCrapsRollService.listCrapsRollsByIdPlayer(p.getIdPlayer()))));

        playerCollections.forEach(p -> p.setCrapsRolls(
                iCrapsRollService.listCrapsRollsByIdPlayer(p.getIdPlayer())));

        return new ResponseEntity<>(
                playerModelAssembler.toCollectionModel(playerCollections),
                HttpStatus.OK);
    }

    /**
     * Mapeo de una peticion HTTP POST, a la URL http://localhost:8082/players/{id}/games
     *
     * Jugador realiza tirada de dados
     *
     * @param idPlayer
     * @return
     */
    @PostMapping("/players/{id}/games")
    public ResponseEntity<?> newCrapsRollPlayer(@PathVariable(name="id") String idPlayer) {
        PlayerCollection playerCollection = iPlayerService.findPlayerById(idPlayer)
                .orElseThrow(() -> new PlayerNotFoundException(idPlayer));

        CrapsRollCollection newCrapsRollCollection = IUtilities.generateNewCrapsRoll(idPlayer);
        CrapsRollCollection crapsRollCollection = iCrapsRollService.saveCrapsRoll(newCrapsRollCollection);

        GameCollection gameCollection = new GameCollection();

        gameCollection.setIdPlayer(playerCollection.getIdPlayer());

        gameCollection.setIdCrapsRoll(crapsRollCollection.getIdCrapsRoll());

        gameCollection.setGameResult(newCrapsRollCollection.getRollResult().equals(7)?true:false);

        iGameService.saveGame(gameCollection);

        return new ResponseEntity<>(
                crapsRollModelAssembler.toModel(crapsRollCollection),
                HttpStatus.OK);

    }

    /**
     * Mapeo de una peticion HTTP GET, a la URL http://localhost:8082/players/{id}/games
     *
     * Listado de tiradas de un jugador
     *
     * @param idPlayer
     * @return
     */
    @GetMapping("/players/{id}/games")
    public ResponseEntity<CollectionModel<CrapsRollModel>> allCrapsRollsByPlayer(
                                                    @PathVariable(name="id") String idPlayer) {
        PlayerCollection player = iPlayerService.findPlayerById(idPlayer)
                .orElseThrow(() -> new PlayerNotFoundException(idPlayer));

        List<CrapsRollCollection> crapsRollsCollections = iCrapsRollService.listCrapsRollsByIdPlayer(idPlayer);

        if (!crapsRollsCollections.isEmpty()){
            return new ResponseEntity<>(
                    crapsRollModelAssembler.toCollectionModel(crapsRollsCollections),
                    HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Mapeo de una peticion HTTP DELETE, a la URL http://localhost:8082/players/{id}/games
     *
     * Elimina tiradas de un jugador
     *
     * @param idPlayer
     * @return
     */
    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<?> deleteCrapsRollsByPlayer(@PathVariable(name="id") String idPlayer) {
        PlayerCollection player = iPlayerService.findPlayerById(idPlayer)
                .orElseThrow(() -> new PlayerNotFoundException(idPlayer));

        List<CrapsRollCollection> crapsRollsByPlayer = iCrapsRollService.listCrapsRollsByIdPlayer(idPlayer);
        iCrapsRollService.deleteCrapsRollsByPlayer(crapsRollsByPlayer);

        List<GameCollection> gamesByPlayer = iGameService.listGamesByIdPlayer(idPlayer);

        gamesByPlayer.forEach(gp -> gp.setIdCrapsRoll("null"));

        return ResponseEntity.noContent().build();
    }

    /**
     * Mapeo de una peticion HTTP GET, a la URL http://localhost:8082/players/ranking
     *
     * Ranking medio, porcentaje medio de exito de todos los jugadores
     *
     * @return
     */
    @GetMapping("/players/ranking")
    public ResponseEntity<?> averageSuccessRankingAllPlayers() {
        List<GameCollection> allGames = iGameService.allGames();

        Double averageRankingAllPlayers = IUtilities.computeAverageRankingAllPlayers(allGames);
        Ranking ranking = new Ranking();
        ranking.setAverageRankingAllPlayers(averageRankingAllPlayers);
        ranking.setGames(allGames);

        return new ResponseEntity<>(
                rankingModelAssembler.toModel(ranking),
                HttpStatus.OK);
    }

    /**
     * Mapeo de una peticion HTTP GET, a la URL http://localhost:8082/players/ranking/loser
     *
     * Jugador con peor porcentaje de exito
     *
     * @return
     */
    @GetMapping("/players/ranking/loser")
    public ResponseEntity<?> playerLoser() {
        List<PlayerCollection> allPlayers = iPlayerService.listPlayers();

        allPlayers.forEach(p -> p.setRanking(
                IUtilities.computeGameSuccessRanking(
                        iGameService.listGamesByIdPlayer(p.getIdPlayer()))));

        Optional<PlayerCollection> playerLoser = IUtilities.getWorstPlayer(allPlayers);

        playerLoser.get().setCrapsRolls(
                iCrapsRollService.listCrapsRollsByIdPlayer(playerLoser.get().getIdPlayer()));

        return new ResponseEntity<>(
                playerModelAssembler.toModel(playerLoser.get()),
                HttpStatus.OK);
    }

    /**
     * Mapeo de una peticion HTTP GET, a la URL http://localhost:8082/players/ranking/winner
     *
     * Jugador con mejor porcentaje de exito
     *
     * @return
     */
    @GetMapping("/players/ranking/winner")
    public ResponseEntity<?> playerWinner() {
        List<PlayerCollection> allPlayers = iPlayerService.listPlayers();

        allPlayers.forEach(p -> p.setRanking(
                IUtilities.computeGameSuccessRanking(
                        iGameService.listGamesByIdPlayer(p.getIdPlayer()))));

        Optional<PlayerCollection> playerWinner = IUtilities.getBestPlayer(allPlayers);

        playerWinner.get().setCrapsRolls(
                iCrapsRollService.listCrapsRollsByIdPlayer(playerWinner.get().getIdPlayer()));

        return new ResponseEntity<>(
                playerModelAssembler.toModel(playerWinner.get()),
                HttpStatus.OK);
    }
}

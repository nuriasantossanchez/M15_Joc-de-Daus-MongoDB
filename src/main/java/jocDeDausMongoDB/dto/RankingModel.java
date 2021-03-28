package jocDeDausMongoDB.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Clase de la capa DTO, representa un recurso que se solicita en una peticion y
 * que sera obtenido en forma de RepresentationModel,  es decir, con enlaces agregados
 */

@NoArgsConstructor
@AllArgsConstructor
public class RankingModel extends RepresentationModel<RankingModel> {

    private Double averageRankingAllPlayers;
    private List<GameModel> games;

    public Double getAverageRankingAllPlayers() {
        return averageRankingAllPlayers;
    }

    public void setAverageRankingAllPlayers(Double averageRankingAllPlayers) {
        this.averageRankingAllPlayers = averageRankingAllPlayers;
    }

    public List<GameModel> getGames() {
        games.sort(comparing(GameModel::getIdPlayer));
        return games;
    }

    public void setGames(List<GameModel> games) {
        this.games = games;
    }

}

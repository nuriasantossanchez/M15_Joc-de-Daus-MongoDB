package jocDeDausMongoDB.service;

import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface de la capa Service
 *
 */

public interface IPlayerService {

    PlayerCollection savePlayer(PlayerCollection playerCollection); // save a playerCollection


    Optional<PlayerCollection> findPlayerById(String idPlayer); // find player by Id

    Page<PlayerCollection> listPlayers(Pageable pageable);

    List<PlayerCollection> listPlayers();

    void deletePlayer(PlayerCollection player);


}

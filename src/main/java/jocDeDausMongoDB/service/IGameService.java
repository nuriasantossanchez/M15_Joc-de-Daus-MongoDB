package jocDeDausMongoDB.service;

import jocDeDausMongoDB.collection.GameCollection;

import java.util.List;

/**
 * Interface de la capa Service
 *
 */

public interface IGameService {

    List<GameCollection> allGames();
    GameCollection saveGame(GameCollection gameCollection);

    List<GameCollection> listGamesByIdPlayer(String idPlayer);

}

package jocDeDausMongoDB.service;

import jocDeDausMongoDB.collection.GameCollection;

import java.util.List;

/**
 * Interface de la capa Service
 *
 */
public interface IGameService {

    Long countAll();

    List<GameCollection> allGames(); // average success ranking of all playerCollections

    GameCollection saveGame(GameCollection gameCollection);

    List<GameCollection> listGamesByIdPlayer(String idPlayer);

}

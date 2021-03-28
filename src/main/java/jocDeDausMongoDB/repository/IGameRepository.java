package jocDeDausMongoDB.repository;

import jocDeDausMongoDB.collection.GameCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


/**
 * Interface de la capa Repository, extiende MongoRepository
 *
 */

@RepositoryRestResource
public interface IGameRepository extends MongoRepository<GameCollection, String> {

    List<GameCollection> findGamesByIdPlayer(String idPlayer);

}

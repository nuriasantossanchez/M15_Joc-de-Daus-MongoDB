package jocDeDausMongoDB.repository;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jocDeDausMongoDB.collection.GameCollection;

import java.util.List;


/**
 * Interface de la capa Repository, extiende JpaRepository
 *
 */
@RepositoryRestResource
public interface IGameRepository extends MongoRepository<GameCollection, String> {

    List<GameCollection> findGamesByIdPlayer(String idPlayer);

}

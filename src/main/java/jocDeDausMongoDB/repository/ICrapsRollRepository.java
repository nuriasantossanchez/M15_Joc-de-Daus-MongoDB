package jocDeDausMongoDB.repository;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Interface de la capa Repository, extiende MongoRepository
 *
 */

@RepositoryRestResource
public interface ICrapsRollRepository extends MongoRepository<CrapsRollCollection, String> {

    List<CrapsRollCollection> findCrapsRollsByIdPlayer(String idPlayer);

}

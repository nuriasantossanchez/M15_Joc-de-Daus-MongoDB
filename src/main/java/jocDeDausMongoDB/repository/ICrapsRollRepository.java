package jocDeDausMongoDB.repository;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface de la capa Repository, extiende JpaRepository
 *
 */
@RepositoryRestResource
public interface ICrapsRollRepository extends MongoRepository<CrapsRollCollection, String> {

    List<CrapsRollCollection> findCrapsRollsByIdPlayer(String idPlayer);

}

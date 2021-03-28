package jocDeDausMongoDB.repository;

import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * Interface de la capa Repository, extiende MongoRepository
 *
 */

@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface IPlayerRepository extends MongoRepository<PlayerCollection, String> {
    Page<PlayerCollection> findAll(Pageable pageable);
    Optional<PlayerCollection> findByName(String name);
}

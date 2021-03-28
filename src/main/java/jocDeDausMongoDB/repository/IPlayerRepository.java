package jocDeDausMongoDB.repository;

import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

/**
 * Interface de la capa Repository, extiende MongoRepository
 *
 */
//@RepositoryRestResource(exported=true, collectionResourceRel = "players", path = "players")
@RepositoryRestResource(collectionResourceRel = "players", path = "players")
//@RepositoryRestResource
//public interface IPlayerRepository extends PagingAndSortingRepository<PlayerCollection, String> {
public interface IPlayerRepository extends MongoRepository<PlayerCollection, String> {
    Page<PlayerCollection> findAll(Pageable pageable);
    Optional<PlayerCollection> findByName(String name);
}

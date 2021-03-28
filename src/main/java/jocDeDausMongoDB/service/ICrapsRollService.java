package jocDeDausMongoDB.service;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface de la capa Service
 *
 */
public interface ICrapsRollService {

    CrapsRollCollection saveCrapsRoll(CrapsRollCollection crapsRollCollection); // save crapsRollCollection

    void deleteCrapsRollsByPlayer(List<CrapsRollCollection> crapsRollCollections); // delete crapsRollCollections by playerCollection

    List<CrapsRollCollection> listCrapsRollsByIdPlayer(String idPlayer);

    Long countAll();

}

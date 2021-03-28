package jocDeDausMongoDB.service;

import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jocDeDausMongoDB.repository.IPlayerRepository;

import java.util.List;
import java.util.Optional;

/**
 * Clase de la capa Service, implementa la interface IPlayerService
 *
 * Anotaciones:
 * @Service
 * Indica que la clase es un "Servicio", esto es, una operacion ofrecida como una interface que esta solo en el modelo,
 * sin un estado encapsulado.
 *
 * Sirve como una especializacion de @Component, lo que permite que las clases de implementacion se detecten
 * automaticamente a traves del escaneo del classpath
 *
 * @Autowired
 * Marca un constructor, campo, metodo setter o metodo de configuracion para ser detectado
 * automaticamente por la funcionalidad de inyeccion de dependencias de Spring
 *
 */

@Service
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    IPlayerRepository iPlayerRepository;


    @Override
    public PlayerCollection savePlayer(PlayerCollection playerCollection) {
        return iPlayerRepository.save(playerCollection);
    }

    @Override
    public Optional<PlayerCollection> findPlayerById(String idPlayer) {
        return iPlayerRepository.findById(idPlayer);
    }


    @Override
    public Page<PlayerCollection> listPlayers(Pageable pageable) {
        return iPlayerRepository.findAll(pageable);
    }

    @Override
    public List<PlayerCollection> listPlayers() {
        return iPlayerRepository.findAll();
    }

    @Override
    public void deletePlayer(PlayerCollection player) {
        iPlayerRepository.delete(player);
    }

}

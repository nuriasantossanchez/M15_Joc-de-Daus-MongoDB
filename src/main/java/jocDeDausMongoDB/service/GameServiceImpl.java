package jocDeDausMongoDB.service;

import jocDeDausMongoDB.collection.GameCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jocDeDausMongoDB.repository.IGameRepository;

import java.util.List;

/**
 * Clase de la capa Service, implementa la interface IGameService
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
public class GameServiceImpl implements IGameService {

    @Autowired
    IGameRepository iGameRepository;

    @Override
    public List<GameCollection> allGames() {
        return iGameRepository.findAll();
    }

    @Override
    public Long countAll() {
        return iGameRepository.count();
    }

    @Override
    public GameCollection saveGame(GameCollection gameCollection) {
        return iGameRepository.save(gameCollection);
    }

    @Override
    public List<GameCollection> listGamesByIdPlayer(String idPlayer) {
        return iGameRepository.findGamesByIdPlayer(idPlayer);
    }
}

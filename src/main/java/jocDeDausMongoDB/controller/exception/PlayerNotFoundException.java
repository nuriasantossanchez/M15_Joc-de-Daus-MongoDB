package jocDeDausMongoDB.controller.exception;

/**
 * Clase de la capa Controller, dentro del paquete Exception
 *
 * Extiende RuntimeException, de tipo unchecked.
 *
 * La exception es lanzada en la ejecucion de ciertos metodos, en los casos
 * en los que la peticion GET no devuelve ningun resultado
 */
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String id) {
        super("Could not find a Player whit ID " + id);
    }
}

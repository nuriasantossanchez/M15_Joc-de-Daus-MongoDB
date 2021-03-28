package jocDeDausMongoDB.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import jocDeDausMongoDB.collection.CrapsRollCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase de la capa de dominio, implementa el patron Data Transfer Object (DTO Pattern) mediante la
 * creacion de un objeto plano (POJO) con una serie de atributos que puedan ser enviados o recuperados
 * del servidor en una sola invocacion (de tal forma que un DTO puede contener informacion de multiples
 * fuentes o tablas y concentrarlas en una unica clase simple, esto es, crear estructuras de datos
 * independientes del modelo de datos, para transmitir informacion entre un cliente y un servidor)
 *
 * Anotaciones:
 * @Component
 * Indica que una clase es un "componente".
 * Estas clases se consideran candidatas para la deteccion automatica cuando se utiliza una configuracion
 * basada en anotaciones y un escaneo de classpath.
 * Tambien se pueden considerar otras anotaciones a nivel de clase como identificacion de un componente,
 * normalmente un tipo especial de componente: por ejemplo, la anotacion @Repository
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Relation(collectionRelation = "players", itemRelation = "player")
public class PlayerModel extends RepresentationModel<PlayerModel> {

    private String idPlayer;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private Date entryDate;
    private String name;
    private Double ranking;
    private List<CrapsRollModel> crapsRolls;

}

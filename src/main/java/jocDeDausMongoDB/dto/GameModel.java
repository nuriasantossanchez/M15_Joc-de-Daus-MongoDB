package jocDeDausMongoDB.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Clase de la capa DTO, representa un recurso que se solicita en una peticion y
 * que sera obtenido en forma de RepresentationModel,  es decir, con enlaces agregados
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Relation(collectionRelation = "games", itemRelation = "game")
public class GameModel extends RepresentationModel<GameModel> {

    private String idGame;
    private String idPlayer;
    private String idCrapsRoll;
    private Boolean gameResult;
}

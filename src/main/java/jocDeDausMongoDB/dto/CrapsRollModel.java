package jocDeDausMongoDB.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Clase de la capa DTO, representa un recurso que se solicita en una peticion y
 * que sera obtenido en forma de RepresentationModel,  es decir, con enlaces agregados
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Relation(collectionRelation = "crapsRolls", itemRelation = "crapsRoll")
public class CrapsRollModel extends RepresentationModel<CrapsRollModel> {

    private String idCrapsRoll;
    private String idPlayer;
    private Short crapOne;
    private Short crapTwo;
    private Short rollResult;

}

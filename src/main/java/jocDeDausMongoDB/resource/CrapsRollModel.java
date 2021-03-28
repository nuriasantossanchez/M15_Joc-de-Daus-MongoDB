package jocDeDausMongoDB.resource;

import jocDeDausMongoDB.collection.PlayerCollection;
import lombok.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

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

package jocDeDausMongoDB.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.List;

/**
 * Clase de la capa DTO, representa un recurso que se solicita en una peticion y
 * que sera obtenido en forma de RepresentationModel,  es decir, con enlaces agregados
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

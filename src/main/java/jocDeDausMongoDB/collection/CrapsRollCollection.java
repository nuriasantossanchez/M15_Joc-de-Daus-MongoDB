package jocDeDausMongoDB.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "crapsRoll")
public class CrapsRollCollection implements Serializable {

    @Id
    private String idCrapsRoll;
    private String idPlayer;
    private Short crapOne;
    private Short crapTwo;
    private Short rollResult;

}

package jocDeDausMongoDB.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "game")
public class GameCollection implements Serializable {

    //Atributos de entidad GameCollection

    @Id
    private String idGame;
    private String idPlayer;
    private String idCrapsRoll;
    private Boolean gameResult;
}

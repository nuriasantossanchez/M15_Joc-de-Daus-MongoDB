package jocDeDausMongoDB.collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jocDeDausMongoDB.util.IUtilities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase de la capa de dominio.
 *
 * La anotacion @Entity indica que la clase es una entidad.
 *
 * La anotacion @Table indica que la clase sera mapeada a una tabla y persistida, en este caso,
 * tanto en una base de datos embebida de tipo H2, como en una base de datos MySQL.
 * (ver application.properties, donde estan definidas ambas conexiones)
 *
 * La anotacion @IdClass especifica que la clase tiene clave primaria compuesta, es decir, se asigna a varios campos
 * o propiedades de la entidad.
 * Los nombres de los campos o propiedades en la clase de clave primaria, en este caso, GamePlayerPk.class,
 * y los campos o propiedades de la clave primaria de la entidad se deben corresponder y sus tipos deben ser los mismos.
 *
 * La anotacion @ManyToOne especifica una asociacion de un solo valor a otra clase de entidad
 * que tiene multiplicidad de muchos a uno, en este caso, la asociacion es con la entidad GameCollection,
 * donde varios valores de tipo PlayerCollection pueden estar asociados a una unica entidad de tipo GameCollection.
 *
 * La anotacion @JoinColumn indica que la propiedad game es el campo para crear la relacion de llave foranea
 * y va a tomar la columna id_shop de la tabla PICTURE para crear el join con la tabla padre SHOP
 *
 * La anotacion @PrePersist especifica un metodo de devolucion de llamada para el evento de ciclo de vida
 * correspondiente, en este caso, define ciertos valores por defecto previos a la insercion en base de datos.
 *
 * La anotacion @Temporal debe especificarse para campos persistentes o propiedades de tipo java.util.Date
 * y java.util.Calendar. Solo se puede especificar para campos o propiedades de este tipo.
 */
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "player")
@JsonPropertyOrder({"idPlayer", "entryDate", "name" , "ranking", "crapsRolls", "games"})
public class PlayerCollection implements Serializable {

    @Id
    private String idPlayer;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date entryDate = new Date();

    @NotNull(message = "name is required")
    private String name;

    private Double ranking = Double.valueOf(0);

    @DBRef(db="crapsRoll")
    private List<CrapsRollCollection> crapsRolls = new ArrayList<>();

    @DBRef(db="game")
    private List<GameCollection> games = new ArrayList<>();


    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Date getEntryDate() {
        if (this.entryDate == null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            this.entryDate = date;
        }

        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getName() {
        this.name=name.trim();

        if (this.name.isBlank()) {
            this.name = "ANONYMOUS";
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRanking() {
        return ranking;
    }

    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public List<CrapsRollCollection> getCrapsRolls() {
        return crapsRolls;
    }

    public void setCrapsRolls(List<CrapsRollCollection> crapsRollCollections) {
        this.crapsRolls = crapsRollCollections;
    }

    public List<GameCollection> getGames() {
        return games;
    }

    public void setGames(List<GameCollection> gameCollections) {
        this.games = gameCollections;
    }
}

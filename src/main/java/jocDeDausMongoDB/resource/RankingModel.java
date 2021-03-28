package jocDeDausMongoDB.resource;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

import static java.util.Comparator.comparing;

@NoArgsConstructor
@AllArgsConstructor
public class RankingModel extends RepresentationModel<RankingModel> {

    private Double averageRankingAllPlayers;
    private List<GameModel> games;

    public Double getAverageRankingAllPlayers() {
        return averageRankingAllPlayers;
    }

    public void setAverageRankingAllPlayers(Double averageRankingAllPlayers) {
        this.averageRankingAllPlayers = averageRankingAllPlayers;
    }

    public List<GameModel> getGames() {
        games.sort(comparing(GameModel::getIdPlayer));
        return games;
    }

    public void setGames(List<GameModel> games) {
        this.games = games;
    }

}

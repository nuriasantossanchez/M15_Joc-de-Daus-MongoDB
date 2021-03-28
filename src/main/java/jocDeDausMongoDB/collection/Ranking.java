package jocDeDausMongoDB.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Ranking{

    private Double averageRankingAllPlayers;
    private List<GameCollection> games;

    public Double getAverageRankingAllPlayers() {
        return averageRankingAllPlayers;
    }

    public void setAverageRankingAllPlayers(Double averageRankingAllPlayers) {
        this.averageRankingAllPlayers = averageRankingAllPlayers;
    }

     public List<GameCollection> getGames() {
        return games;
    }

    public void setGames(List<GameCollection> games) {
        this.games = games;
    }

 }

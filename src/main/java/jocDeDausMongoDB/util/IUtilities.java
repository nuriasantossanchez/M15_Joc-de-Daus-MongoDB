package jocDeDausMongoDB.util;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.GameCollection;
import jocDeDausMongoDB.collection.PlayerCollection;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.counting;


public interface IUtilities {

    static ToDoubleBiFunction<Double, Double> computeAverageFunction = (a, b) -> a/b*100;

    static Optional<PlayerCollection> checkUniqueNickName(PlayerCollection newPlayerCollection, List<PlayerCollection> playerCollections) {
        return playerCollections.stream()
                .filter(p -> !p.getName().toUpperCase().equals("ANONYMOUS")
                        && !newPlayerCollection.getName().isBlank()
                        && p.getName().toUpperCase().contains(newPlayerCollection.getName().trim().toUpperCase()))
                .findFirst();
    }

    static Double computePlayerSuccessRanking(List<CrapsRollCollection> crapsRollCollections) {
        double successRanking = Double.valueOf(0);

        if (null != crapsRollCollections && !crapsRollCollections.isEmpty()){

            Long wins = getWinsCrapsRolls(crapsRollCollections);

            //successRanking = (double) wins/crapsRollCollections.stream().count()*100;
            successRanking = computeAverageFunction.applyAsDouble((double) wins, (double) crapsRollCollections.stream().count());

            return Math.round(successRanking*100.0)/100.0;
        }

        return successRanking;
    }

     static Long getWinsCrapsRolls(List<CrapsRollCollection> crapsRollCollections) {

        Long wins = crapsRollCollections.stream()
                .filter(r -> r.getRollResult() == 7)
                .count();
        return wins;

    }

     static CrapsRollCollection generateNewCrapsRoll(String idPlayer) {
        CrapsRollCollection crapsRollCollection = new CrapsRollCollection();

        crapsRollCollection.setIdPlayer(idPlayer);

        List<Short> randomCrapsRoll = getRandomNumbers();

        crapsRollCollection.setCrapOne(randomCrapsRoll.get(0));
        crapsRollCollection.setCrapTwo(randomCrapsRoll.get(1));
        crapsRollCollection.setRollResult((short) (crapsRollCollection.getCrapOne()
                + crapsRollCollection.getCrapTwo()));
        return crapsRollCollection;
    }

     static List<Short> getRandomNumbers(){

        Random random = new Random();
        Integer max = 6;
        Integer min = 1;

        List<Short> randomNumbers = Stream.generate(()->random.nextInt((max - min) + 1) + min)
                .distinct()
                .limit(2)
                .map(r -> r.shortValue())
                .collect(toList());

        return randomNumbers;
    }

    static Double computeAverageRankingAllPlayers(List<GameCollection> games) {

        Map<String, Long> gamesByPlayer = getGamesByPlayer(games);

        Long wins = getWinsGames(games);

        Long totalGames = gamesByPlayer.values().stream().reduce(Long::sum).get();

        double averageRanking = computeAverageFunction.applyAsDouble((double) wins, (double) totalGames)
                /gamesByPlayer.size();

        return Math.round(averageRanking*100.0)/100.0;

    }
    static Long getWinsGames(List<GameCollection> games) {

        Long wins =
                games.stream()
                        .filter(g -> g.getGameResult() == true)
                        .collect(counting());
        return wins;
    }

    static Map<String, Long> getGamesByPlayer(List<GameCollection> games) {

        Map<String, Long> gamesByPlayer = games.stream().collect(
                groupingBy(GameCollection::getIdPlayer, counting()));

        return gamesByPlayer;
    }

    static Optional<PlayerCollection> getWorstPlayer(List<PlayerCollection> players) {

        return Optional.of(players.stream()
                .collect(reducing((p1, p2) -> p1.getRanking() < p2.getRanking() ? p1 : p2)).get());
    }

    static Optional<PlayerCollection> getBestPlayer(List<PlayerCollection> players) {

        return Optional.of(players.stream()
                .collect(reducing((p1, p2) -> p1.getRanking() > p2.getRanking() ? p1 : p2)).get());

    }

    static Double computeGameSuccessRanking(List<GameCollection> games) {

        double successRanking = Double.valueOf(0);

        if (null != games && !games.isEmpty()){

            Long wins = getWinsGames(games);

            successRanking = computeAverageFunction.applyAsDouble((double) wins,
                    (double)games.stream().count());

            return Math.round(successRanking*100.0)/100.0;
        }

        return successRanking;
    }

}

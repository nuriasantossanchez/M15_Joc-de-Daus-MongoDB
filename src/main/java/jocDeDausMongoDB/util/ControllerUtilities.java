package jocDeDausMongoDB.util;

import jocDeDausMongoDB.collection.CrapsRollCollection;
import jocDeDausMongoDB.collection.PlayerCollection;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.ToDoubleBiFunction;

import static java.util.stream.Collectors.reducing;

@Component
public class ControllerUtilities implements IUtilities {

    ToDoubleBiFunction<Double, Double> computeAverageFunction = (a, b) -> a/b*100;

    public Optional<PlayerCollection> checkUniqueNickName(PlayerCollection newPlayerCollection, List<PlayerCollection> playerCollections) {

        return playerCollections.stream()
                .filter(p -> !p.getName().toUpperCase().equals("ANONYMOUS")
                        && !newPlayerCollection.getName().isBlank()
                        && p.getName().toUpperCase().contains(newPlayerCollection.getName().trim().toUpperCase()))
                .findFirst();
    }

    public Double computePlayerSuccessRanking(List<CrapsRollCollection> crapsRollCollections) {

        double successRanking = Double.valueOf(0);

        if (null != crapsRollCollections && !crapsRollCollections.isEmpty()){

            Long wins = getWinsCrapsRolls(crapsRollCollections);

            //successRanking = (double) wins/crapsRollCollections.stream().count()*100;
            successRanking = computeAverageFunction.applyAsDouble((double) wins, (double) crapsRollCollections.stream().count());

            return Math.round(successRanking*100.0)/100.0;
        }

        return successRanking;
    }

    public Long getWinsCrapsRolls(List<CrapsRollCollection> crapsRollCollections) {

        Long wins = crapsRollCollections.stream()
                .filter(r -> r.getRollResult() == 7)
                .count();
        return wins;

    }


}

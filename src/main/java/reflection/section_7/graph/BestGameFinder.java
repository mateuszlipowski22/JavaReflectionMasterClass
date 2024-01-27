package reflection.section_7.graph;

import reflection.section_7.graph.annotations.Annotations.*;
import reflection.section_7.graph.databases.Database;

import java.util.*;

public class BestGameFinder {
    Database database = new Database();

    @Operation("All-Games")
    public Set<String> getAllGames() {
        return database.readAllGames();
    }
    @Operation("Game-To-Price")
    public Map<String, Float> getGameToPrice(@DependsOn("All-Games") Set<String> allGames){
        return database.readGameToPrice(allGames);
    }
    @Operation("Game-To-Rating")
    public Map<String, Float> getGameToRating(@DependsOn("All-Games") Set<String> allGames){
        return database.readGameToRatings(allGames);
    }

    @Operation("Score-To-Games")
    public SortedMap<Double,String> scoreGames(
            @DependsOn("Game-To-Price") Map<String, Float> gameToPrice,
            @DependsOn("Game-To-Rating") Map<String, Float> gameToRating){
        SortedMap<Double,String> gameToScore = new TreeMap<>(Collections.reverseOrder());
        for(String gameName : gameToPrice.keySet()){
            double score = (double) gameToRating.get(gameName);
            gameToScore.put(score,gameName);
        }
        return gameToScore;
    }

    @FinalResult
    public List<String> getTopGames(@DependsOn("Score-To-Games") SortedMap<Double,String> gameToScore){
        return new ArrayList<>(gameToScore.values());
    }
}

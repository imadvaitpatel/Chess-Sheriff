package com.advait.chessdumbcheater.models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GameSetStats {
    private int numGames;
    private double totalScore;
    private List<Double> capsScores;
    private List<Double> averageMoveTimeGameList;
    private TreeMap<Double, Integer> moveTimeRangeFromAverage;

    public GameSetStats()
    {
        numGames = 0;
        totalScore = 0.0;
        capsScores = new ArrayList<>();
        averageMoveTimeGameList = new ArrayList<>();
        moveTimeRangeFromAverage = getMoveTimeRangeFromAverageMap();
    }

    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public List<Double> getCapsScores() {
        return capsScores;
    }

    public List<Double> getAverageMoveTimeGameList() {
        return averageMoveTimeGameList;
    }

    public TreeMap<Double, Integer> getMoveTimeRangeFromAverage() {
        return moveTimeRangeFromAverage;
    }

    private TreeMap<Double, Integer> getMoveTimeRangeFromAverageMap() {
        // This map keeps track of number of moves within a range of seconds from the average move time of a game
        // Keys are mapped to the upper bound of the range
        TreeMap<Double, Integer> moveTimeRangeFromAverage = new TreeMap<>();

        moveTimeRangeFromAverage.put(0.5, 0);
        moveTimeRangeFromAverage.put(1.0, 0);
        moveTimeRangeFromAverage.put(2.0, 0);
        moveTimeRangeFromAverage.put(3.0, 0);
        moveTimeRangeFromAverage.put(5.0, 0);
        moveTimeRangeFromAverage.put(10.0, 0);
        moveTimeRangeFromAverage.put(Double.POSITIVE_INFINITY, 0);

        return moveTimeRangeFromAverage;
    }
}

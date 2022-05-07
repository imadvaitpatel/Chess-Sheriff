package com.advait.chesscheatstats.models;

import java.util.HashMap;

public class PlayerStatsDTO {
    private int totalGames;
    private double overallScore;
    private double overallLowestCapsScore;
    private double overallHighestCapsScore;
    private double overallAverageCapsScore;
    private int totalCapsGames;
    private HashMap<String, GameSetStats> gameStatsByTimeControl;

    public PlayerStatsDTO()
    {
        totalGames = 0;
        overallScore = 0.0;
        overallLowestCapsScore = Double.NaN;
        overallHighestCapsScore = Double.NaN;
        overallAverageCapsScore = Double.NaN;
        totalCapsGames = 0;
        gameStatsByTimeControl = new HashMap<>();
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(double overallScore) {
        this.overallScore = overallScore;
    }

    public double getOverallLowestCapsScore() {
        return overallLowestCapsScore;
    }

    public void setOverallLowestCapsScore(double overallLowestCapsScore) {
        this.overallLowestCapsScore = overallLowestCapsScore;
    }

    public double getOverallHighestCapsScore() {
        return overallHighestCapsScore;
    }

    public void setOverallHighestCapsScore(double overallHighestCapsScore) {
        this.overallHighestCapsScore = overallHighestCapsScore;
    }

    public double getOverallAverageCapsScore() {
        return overallAverageCapsScore;
    }

    public void setOverallAverageCapsScore(double overallAverageCapsScore) {
        this.overallAverageCapsScore = overallAverageCapsScore;
    }

    public int getTotalCapsGames() {
        return totalCapsGames;
    }

    public void setTotalCapsGames(int totalCapsGames) {
        this.totalCapsGames = totalCapsGames;
    }

    public HashMap<String, GameSetStats> getGameStatsByTimeControl() {
        return gameStatsByTimeControl;
    }

    public void setGameStatsByTimeControl(HashMap<String, GameSetStats> gameStatsByTimeControl) {
        this.gameStatsByTimeControl = gameStatsByTimeControl;
    }
}

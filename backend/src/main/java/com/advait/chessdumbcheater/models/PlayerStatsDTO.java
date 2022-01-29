package com.advait.chessdumbcheater.models;

import java.util.HashMap;

public class PlayerStatsDTO {
    private int totalGames;
    private double overallWinRate;
    private double lowestCapsScore;
    private double highestCapsScore;
    private double totalAverageCapScore;
    private int totalCapsGames;
    private HashMap<String, GameSetStat> gameByTimeControlStats;

    class GameSetStat {
        private int numGames;
        private double winRate;
        private double averageCapScore;
        private int numCapsGames;
    }
}

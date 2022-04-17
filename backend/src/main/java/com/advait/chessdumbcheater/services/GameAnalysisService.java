package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.Game;
import com.advait.chessdumbcheater.models.GameSetStats;
import com.advait.chessdumbcheater.models.PlayerStatsDTO;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GameAnalysisService {

    public PlayerStatsDTO analyzeGames(String playerName, List<Game> games) {
        HashMap<String, GameSetStats> gamesByTimeControl = new HashMap<>();

        double overAllScore = 0.0;
        double lowestCapsScoreAllTCs = Double.POSITIVE_INFINITY;
        double highestCapsScoreAllTCs = Double.NEGATIVE_INFINITY;
        double totalCapsScoreAllTCs = 0.0;
        int totalCapsGamesAllTCs = 0;

        for (Game game : games) {
            // only chess and chess960 is currently supported
            if (!game.getRules().equalsIgnoreCase("chess") && !game.getRules().equalsIgnoreCase("chess960"))
            {
                continue;
            }
            boolean isWhite = game.getWhite().getUsername().equalsIgnoreCase(playerName);
            if (!gamesByTimeControl.containsKey(game.getTime_control())) {
                GameSetStats gameSetStats = new GameSetStats();
                gamesByTimeControl.put(game.getTime_control(), gameSetStats);
            }

            GameSetStats gameSetStats = gamesByTimeControl.get(game.getTime_control());

            int totalGames = gameSetStats.getNumGames() + 1;

            double score = Double.NaN;
            if (isWhite) {
                score = getResultScore(game.getWhite().getResult());
            }
            else {
                score = getResultScore(game.getBlack().getResult());
            }
            overAllScore += score;

            double capsScore = Double.NaN;
            if (game.getAccuracies() != null) {
                if (isWhite) {
                    capsScore = game.getAccuracies().getWhite();
                }
                else {
                    capsScore = game.getAccuracies().getBlack();
                }
                totalCapsGamesAllTCs += 1;
                totalCapsScoreAllTCs += capsScore;
                highestCapsScoreAllTCs = Math.max(highestCapsScoreAllTCs, capsScore);

                if (capsScore != 0)
                {
                    lowestCapsScoreAllTCs = Math.min(lowestCapsScoreAllTCs, capsScore);
                }
            }

            // analyze move times
            TreeMap<Double, Integer> moveTimeRangeFromAverage = gameSetStats.getMoveTimeRangeFromAverage();
            List<Double> clockTimesInSeconds = getClockTimesFromPgnString(playerName, game);

            double increment = game.getIncrement();
            List<Double> moveTimes = new ArrayList<>();
            for (int i = 1; i < clockTimesInSeconds.size(); i++) {
                moveTimes.add(clockTimesInSeconds.get(i-1) - clockTimesInSeconds.get(i) + increment);
            }
            double averageMoveTime = moveTimes.stream().mapToDouble(a-> a).average().orElse(0.0);

            for (Double moveTime : moveTimes) {
                Double key = moveTimeRangeFromAverage.ceilingKey(Math.abs(averageMoveTime - moveTime));
                moveTimeRangeFromAverage.put(key, moveTimeRangeFromAverage.get(key) + 1);
            }

            // update other stats
            gameSetStats.setNumGames(totalGames);
            gameSetStats.setTotalScore(gameSetStats.getTotalScore() + score);
            gameSetStats.getCapsScores().add(capsScore);
            gameSetStats.getAverageMoveTimeGameList().add(averageMoveTime);
        }

        PlayerStatsDTO playerStats = new PlayerStatsDTO();
        playerStats.setTotalGames(games.size());
        playerStats.setOverallScore(overAllScore);
        playerStats.setTotalCapsGames(totalCapsGamesAllTCs);
        playerStats.setOverallAverageCapsScore(totalCapsScoreAllTCs / totalCapsGamesAllTCs);
        playerStats.setGameStatsByTimeControl(gamesByTimeControl);
        playerStats.setOverallLowestCapsScore(lowestCapsScoreAllTCs == Double.POSITIVE_INFINITY ? Double.NaN : lowestCapsScoreAllTCs);
        playerStats.setOverallHighestCapsScore(highestCapsScoreAllTCs == Double.NEGATIVE_INFINITY ? Double.NaN : highestCapsScoreAllTCs);

        return playerStats;
    }

    /**
     * Return a list of clock times, where the value at index i is the clock time on move i
     * for the given player during the game given game.
     * @param playerName - name of player
     * @param game - game played by player
     * @return list of clock times for player during this game
     */
    private List<Double> getClockTimesFromPgnString(String playerName, Game game) {
        List<Double> clockTimesInSeconds = new ArrayList<>();

        clockTimesInSeconds.add(Double.valueOf(game.getBaseTimeInSeconds()));

        List<String> allClockTimes = new ArrayList<>();

        // use regex to find clock times
        String regex = "\\{\\[%clk\\s(.*?)\\]\\}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(game.getPgn());

        while (m.find())
        {
            String s = m.group(1);
            allClockTimes.add(s);
        }

        // even indices have white's clock times, odd indices have black's clock times
        int i = game.getWhite().getUsername().equalsIgnoreCase(playerName) ? 0 : 1;

        while (i < allClockTimes.size()) {
            String clockTime = allClockTimes.get(i);
            double timeInSeconds = convertClockStringToSeconds(clockTime);
            clockTimesInSeconds.add(timeInSeconds);
            i += 2;
        }

        return clockTimesInSeconds;
    }

    /**
     *  Converts a string representation of a clock time to a time in seconds
     * @param clockTime - string of form H:MM:SS e.g. 0:02:59 OR of form H:MM:SS.m e.g. 0:02:59.3
     * @return time in seconds
     */
    private double convertClockStringToSeconds(String clockTime) {
        double seconds = 0;
        String[] clockTokens = clockTime.split(":");
        seconds += 3600 * Integer.parseInt(clockTokens[0]);
        seconds += 60 * Integer.parseInt(clockTokens[1]);
        seconds += Double.parseDouble(clockTokens[2]);
        return seconds;
    }

    private double getResultScore(String result) {
        switch (result.toLowerCase()) {
            case "win":
                return 1.0;
            case "stalemate":
            case "repetition":
            case "insufficient":
            case "agreed":
            case "50move":
            case "timevsinsufficient":
                return 0.5;
            case "checkmated":
            case "timeout":
            case "resigned":
            case "lose":
            case "abandoned":
            case "kingofthehill":
            case "threecheck":
            case "bughousepartnerlose":
                return 0.0;
            default:
                throw new NotImplementedException();
        }
    }
}

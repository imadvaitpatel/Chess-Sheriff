package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.Game;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GameAnalysisService {

    public TreeMap<Double, Integer> getMoveTimeRangeFromAverageMap() {
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

    public void analyzeGames(String playerName, List<Game> games) {
        TreeMap<Double, Integer> moveTimeRangeFromAverage = getMoveTimeRangeFromAverageMap();
        for (Game game : games) {
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
        }

        List<Integer> test = new ArrayList<>();
        test.add(4);
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
}

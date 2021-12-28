package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GameAnalysisService {

    public void analyzeGames(String playerName, List<Game> games) {
        for (Game game : games) {
            List<Double> clockTimesInSeconds = getClockTimesFromPgnString(playerName, game);
        }
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
        seconds += 3600 * Character.getNumericValue(clockTime.charAt(0));
        seconds += 60 * Integer.parseInt(clockTime.substring(2, 4));
        seconds += Integer.parseInt(clockTime.substring(5, 7));
        if (clockTime.length() > 8) {
            seconds += 0.1 * Character.getNumericValue(clockTime.charAt(8));
        }
        return seconds;
    }
}

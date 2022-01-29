package com.advait.chessdumbcheater.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private GamePlayer white;
    private GamePlayer black;
    private GameAccuracy accuracies;
    private String url;
    private String pgn;
    private String time_control;
    public String rules;

    public GamePlayer getWhite() {
        return white;
    }

    public void setWhite(GamePlayer white) { this.white = white; }

    public GamePlayer getBlack() {
        return black;
    }

    public void setBlack(GamePlayer black) {
        this.black = black;
    }

    public void setAccuracies(GameAccuracy accuracies) {
        this.accuracies = accuracies;
    }

    public GameAccuracy getAccuracies() {
        return accuracies;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPgn() { return pgn; }

    public void setPgn(String pgn) { this.pgn = pgn; }

    public void setTime_control(String time_control) {
        this.time_control = time_control;
    }

    public String getTime_control() {
        return time_control;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getRules() {
        return rules;
    }

    public long getId() {
        if (this.url == null) {
            return -1;
        }
        else {
            String[] tokens = this.url.split("/");
            return Long.parseLong(tokens[tokens.length - 1]);
        }
    }

    public Double getBaseTimeInSeconds() {
        String[] timeControl = this.time_control.split("\\+");
        if (timeControl[0].contains("/")) {
            String[] rat = timeControl[0].split("/");
            return Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]);
        }
        else {
            return Double.parseDouble(timeControl[0]);
        }
    }

    public Double getIncrement() {
        String[] timeControl = this.time_control.split("\\+");
        if (timeControl.length == 1) {
            return 0.0;
        }
        if (timeControl[1].contains("/")) {
            String[] rat = timeControl[1].split("/");
            return Double.parseDouble(rat[1]) / Double.parseDouble(rat[1]);
        }
        else {
            return Double.parseDouble(timeControl[1]);
        }
    }
}

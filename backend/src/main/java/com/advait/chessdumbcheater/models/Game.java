package com.advait.chessdumbcheater.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private GamePlayer white;
    private GamePlayer black;
    private String url;

    public GamePlayer getWhite() {
        return white;
    }

    public void setWhite(GamePlayer white) {
        this.white = white;
    }

    public GamePlayer getBlack() {
        return black;
    }

    public void setBlack(GamePlayer black) {
        this.black = black;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        if (this.url == null) {
            return -1;
        }
        else {
            String[] tokens = this.url.split("/");
            return Integer.parseInt(tokens[tokens.length - 1]);
        }
    }
}

package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.Game;
import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;

@Service
public class GameStatisticService {
    public Double getCapsScore(Game game) {
        long id = game.getId();
        String url = String.format("https://www.chess.com/analysis/game/live/%f", id);

        // use JSoup web scraping to get CAPS score later

        return 0.0;
    }
}

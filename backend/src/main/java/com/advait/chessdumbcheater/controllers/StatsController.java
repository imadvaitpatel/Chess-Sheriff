package com.advait.chessdumbcheater.controllers;

import com.advait.chessdumbcheater.models.Game;
import com.advait.chessdumbcheater.services.GameRetrieverService;
import com.advait.chessdumbcheater.services.GameStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class StatsController {

    private final GameRetrieverService gameRetrieverService;
    private final GameStatisticService gameStatisticService;

    @Autowired
    public StatsController(GameRetrieverService gameRetrieverService, GameStatisticService gameStatisticService) {
        this.gameRetrieverService = gameRetrieverService;
        this.gameStatisticService = gameStatisticService;
    }

    @GetMapping("/stats/{playerName}")
    public ResponseEntity<List<String>> getPlayerStats(@PathVariable String playerName, @RequestParam("pastMonths") int pastMonths) {
        List<Game> games =  gameRetrieverService.getPlayerGames(playerName, pastMonths);
        for (Game game : games) {
            gameStatisticService.getCapsScore(game);
        }

        return ResponseEntity.ok(Arrays.asList("hi"));
    }
}

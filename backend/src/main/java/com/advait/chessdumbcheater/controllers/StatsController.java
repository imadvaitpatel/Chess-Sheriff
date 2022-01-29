package com.advait.chessdumbcheater.controllers;

import com.advait.chessdumbcheater.models.Game;
import com.advait.chessdumbcheater.models.PlayerStatsDTO;
import com.advait.chessdumbcheater.services.GameRetrieverService;
import com.advait.chessdumbcheater.services.GameAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class StatsController {

    private final GameRetrieverService gameRetrieverService;
    private final GameAnalysisService gameAnalysisService;

    @Autowired
    public StatsController(GameRetrieverService gameRetrieverService, GameAnalysisService gameAnalysisService) {
        this.gameRetrieverService = gameRetrieverService;
        this.gameAnalysisService = gameAnalysisService;
    }

    @GetMapping("/stats/{playerName}")
    public ResponseEntity<PlayerStatsDTO> getPlayerStats(@PathVariable String playerName, @RequestParam("pastMonths") int pastMonths) {
        List<Game> games =  gameRetrieverService.getPlayerGames(playerName, pastMonths);
        return ResponseEntity.ok(gameAnalysisService.analyzeGames(playerName, games));
    }
}

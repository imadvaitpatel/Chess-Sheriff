package com.advait.chessdumbcheater.controllers;

import com.advait.chessdumbcheater.models.Game;
import com.advait.chessdumbcheater.services.GameRetrieverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class StatsController {

    private final static GameRetrieverService gameRetrieverService = new GameRetrieverService();

    @GetMapping("/stats/{playerName}")
    public ResponseEntity<List<String>> getPlayerStats(@PathVariable String playerName, @RequestParam("pastMonths") int pastMonths) {
        List<Game> games =  gameRetrieverService.getPlayerGames(playerName, pastMonths);

        return ResponseEntity.ok(Arrays.asList("hi"));
    }
}

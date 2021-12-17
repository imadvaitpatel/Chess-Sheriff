package com.advait.chessdumbcheater.controllers;

import com.advait.chessdumbcheater.services.GameRetrieverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class StatsController {

    private final static GameRetrieverService gameRetrieverService = new GameRetrieverService();

    @GetMapping("/stats/{playerName}")
    public ResponseEntity<List<String>> getPlayerStats(@PathVariable String playerName, @RequestParam("pastMonths") int pastMonths) {
        System.out.println("endpoint works");
        System.out.println(gameRetrieverService.getPlayerArchives(playerName, pastMonths));
        return ResponseEntity.ok(Arrays.asList("hi"));
    }
}

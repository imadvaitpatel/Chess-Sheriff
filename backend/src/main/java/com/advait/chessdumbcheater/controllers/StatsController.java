package com.advait.chessdumbcheater.controllers;

import com.advait.chessdumbcheater.services.GameRetrieverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class StatsController {

    private final static GameRetrieverService gameRetrieverService = new GameRetrieverService();

    @GetMapping("/stats")
    public List<String> getPlayerStats(@RequestParam String playerName, @RequestParam(required = false) String dateRange) {
        System.out.println("endpoint works");
        System.out.println(gameRetrieverService.getPlayerArchives(playerName));
        return Arrays.asList("hi");
    }
}

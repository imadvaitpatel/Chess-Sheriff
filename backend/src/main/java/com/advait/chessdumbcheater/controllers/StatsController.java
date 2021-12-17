package com.advait.chessdumbcheater.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class StatsController {

    @GetMapping("/stats")
    public List<String> getPlayerStats(@RequestParam String playerName, @RequestParam(required = false) String dateRange) {
        System.out.println("endpoint works");
        return Arrays.asList("hi");
    }
}

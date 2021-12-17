package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.Archives;
import org.springframework.web.client.RestTemplate;

public class GameRetrieverService {

    private final RestTemplate restTemplate = new RestTemplate();

    private String getChessComArchiveUrl(String playerName) {
        return "https://api.chess.com/pub/player/" + playerName + "/games/archives";
    }

    public Archives getPlayerArchives(String playerName) {
        String url = getChessComArchiveUrl(playerName);
        Archives playerArchives = restTemplate.getForObject(url, Archives.class);
        System.out.println(playerArchives);
        return playerArchives;
    }
}

package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.ArchivesDTO;
import com.advait.chessdumbcheater.models.Game;
import com.advait.chessdumbcheater.models.GamesDTO;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameRetrieverService {

    private final RestTemplate restTemplate = new RestTemplate();

    private String getChessComArchiveUrl(String playerName) {
        return "https://api.chess.com/pub/player/" + playerName + "/games/archives";
    }

    public List<Game> getPlayerGames(String playername, int pastMonths) {
        ArchivesDTO archivesDTO = getPlayerArchives(playername, pastMonths);
        List<Game> games = new ArrayList<>();
        for (String archiveURL : archivesDTO.getArchives()) {
            GamesDTO gamesObject = restTemplate.getForObject(archiveURL, GamesDTO.class);
            games.addAll(gamesObject.getGames());
        }
        return games;
    }

    private ArchivesDTO getPlayerArchives(String playerName, int pastMonths) {
        String url = getChessComArchiveUrl(playerName);
        ArchivesDTO playerArchivesDTO = restTemplate.getForObject(url, ArchivesDTO.class);
        playerArchivesDTO.setArchives(filterArchivesInPastTimePeriod(playerArchivesDTO.getArchives(), pastMonths));
        System.out.println(playerArchivesDTO);
        return playerArchivesDTO;
    }

    private List<String> filterArchivesInPastTimePeriod(List<String> archives, int pastMonths) {
        LocalDate date = LocalDate.now().minus(Period.ofMonths(pastMonths));
        int beginMonth = date.getMonth().getValue(), beginYear = date.getYear();

        return archives.stream().filter(url -> {
            String[] urlTokens = url.split("/");
            int month = Integer.parseInt(urlTokens[urlTokens.length - 1]);
            int year = Integer.parseInt(urlTokens[urlTokens.length - 2]);

            if (beginYear == year) {
                return month >= beginMonth;
            }
            return year > beginYear;
        }).collect(Collectors.toList());
    }
}

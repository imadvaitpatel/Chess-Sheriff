package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.ArchivesDTO;
import com.advait.chessdumbcheater.models.Game;
import com.advait.chessdumbcheater.models.GamesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameRetrieverService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Game> getPlayerGames(String playername, int pastMonths) {
        ArchivesDTO archivesDTO = getPlayerArchives(playername, pastMonths);
        List<Game> games = new ArrayList<>();
        for (String archiveURL : archivesDTO.getArchives()) {
            GamesDTO gamesObject = this.webClientBuilder
                    .build()
                    .get()
                    .uri(archiveURL)
                    .retrieve()
                    .bodyToMono(GamesDTO.class)
                    .block();
            games.addAll(gamesObject.getGames());
        }
        return games;
    }

    private ArchivesDTO getPlayerArchives(String playerName, int pastMonths) {
        ArchivesDTO playerArchivesDTO = this.webClientBuilder
                .build()
                .get()
                .uri("/pub/player/" + playerName + "/games/archives")
                .retrieve()
                .bodyToMono(ArchivesDTO.class)
                .block();
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

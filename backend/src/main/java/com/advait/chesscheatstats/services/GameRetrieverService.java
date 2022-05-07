package com.advait.chesscheatstats.services;

import com.advait.chesscheatstats.models.ArchivesDTO;
import com.advait.chesscheatstats.models.Game;
import com.advait.chesscheatstats.models.GamesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameRetrieverService {

    private WebClient.Builder webClientBuilder;

    @Autowired
    public void setWebClientBuilder(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Game> getPlayerGames(String playerName, int pastMonths) {
        ArchivesDTO archivesDTO = getPlayerArchives(playerName, pastMonths);
        List<Game> games = new ArrayList<>();
        WebClient webClient = this.webClientBuilder.build();
        for (String archiveURL : archivesDTO.getArchives()) {
            GamesDTO archivedGames = webClient
                    .get()
                    .uri(archiveURL)
                    .retrieve()
                    .bodyToMono(GamesDTO.class)
                    .block();
            if (archivedGames != null) {
                games.addAll(archivedGames.getGames());
            }
        }
        return games;
    }

    private ArchivesDTO getPlayerArchives(String playerName, int pastMonths) {
        ArchivesDTO playerArchivesDTO = this.webClientBuilder
                .build()
                .get()
                .uri("https://api.chess.com/pub/player/" + playerName.toLowerCase() + "/games/archives")
                .retrieve()
                .bodyToMono(ArchivesDTO.class)
                .block();
        if (playerArchivesDTO != null) {
            playerArchivesDTO.setArchives(filterArchivesInPastTimePeriod(playerArchivesDTO.getArchives(), pastMonths));
        }
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

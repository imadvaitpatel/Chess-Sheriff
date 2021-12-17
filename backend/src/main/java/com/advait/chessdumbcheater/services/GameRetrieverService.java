package com.advait.chessdumbcheater.services;

import com.advait.chessdumbcheater.models.Archives;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class GameRetrieverService {

    private final RestTemplate restTemplate = new RestTemplate();

    private String getChessComArchiveUrl(String playerName) {
        return "https://api.chess.com/pub/player/" + playerName + "/games/archives";
    }

    public Archives getPlayerArchives(String playerName, int pastMonths) {
        String url = getChessComArchiveUrl(playerName);
        Archives playerArchives = restTemplate.getForObject(url, Archives.class);
        playerArchives.setArchives(filterArchivesInPastTimePeriod(playerArchives.getArchives(), pastMonths));
        System.out.println(playerArchives);
        return playerArchives;
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

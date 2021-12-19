package com.advait.chessdumbcheater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ChessDumbCheaterApplication {

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return   WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build());
    }

    public static void main(String[] args) {
        SpringApplication.run(ChessDumbCheaterApplication.class, args);
    }


}

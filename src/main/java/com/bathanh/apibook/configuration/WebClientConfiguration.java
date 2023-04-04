package com.bathanh.apibook.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.itbook.store/1.0")
                .defaultHeaders(headers -> headers.setContentType(MediaType.APPLICATION_JSON))
                .build();
    }
}

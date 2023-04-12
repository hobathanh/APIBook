package com.bathanh.apibook.configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.singletonList;

@Configuration
public class GoogleTokenVerifierConfig {

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier(@Value("${spring.security.oauth2.client.registration.google.client-id}") final String clientId) {
        return new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(singletonList(clientId))
                .build();
    }
}

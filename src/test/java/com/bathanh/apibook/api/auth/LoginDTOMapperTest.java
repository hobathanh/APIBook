package com.bathanh.apibook.api.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginDTOMapperTest {

    @Test
    void shouldToAuthentication_OK() {
        LoginDTO loginDTO = LoginDTO.builder()
                .username("user1")
                .password("pass1")
                .build();

        Authentication authentication = LoginDTOMapper.toAuthentication(loginDTO);

        assertEquals(loginDTO.getUsername(), authentication.getPrincipal());
        assertEquals(loginDTO.getPassword(), authentication.getCredentials());
        assertEquals(UsernamePasswordAuthenticationToken.class, authentication.getClass());
    }
}
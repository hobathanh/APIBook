package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.properties.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceTest {

    @InjectMocks
    private JwtTokenService jwtTokenService;
    @Mock
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        jwtProperties = new JwtProperties();
        jwtProperties.setExpiration((long) (3600));
        jwtProperties.setSecret("token-secret");
    }

    @Test
    void shouldGenerateToken_OK() {

    }

    @Test
    void shouldParse_OK() {

    }
}

package com.bathanh.apibook.configuration;

import com.bathanh.apibook.domain.auths.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenAuthorizationFilterTest {

    @InjectMocks
    private JwtTokenAuthorizationFilter filter;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Authentication authentication;

    @Test
    void shouldDoFilterInternal_OK() throws ServletException, IOException {
        final String token = randomAlphabetic(3, 10);
        final String authorizationHeader = "Bearer " + token;

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authorizationHeader);
        when(jwtTokenService.parse(token)).thenReturn(authentication);

        filter.doFilterInternal(request, response, filterChain);

        verify(jwtTokenService).parse(token);
        verify(filterChain).doFilter(request, response);
    }
}
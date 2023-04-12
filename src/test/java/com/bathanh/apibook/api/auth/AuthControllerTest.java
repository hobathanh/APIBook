package com.bathanh.apibook.api.auth;

import com.bathanh.apibook.api.AbstractControllerTest;
import com.bathanh.apibook.domain.auths.JwtTokenService;
import com.bathanh.apibook.domain.auths.JwtUserDetails;
import com.bathanh.apibook.domain.auths.SocialLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static com.bathanh.apibook.fakes.AuthFakes.buildAuth;
import static com.bathanh.apibook.fakes.JwtFakes.buildJwtUserDetails;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/auths";

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private SocialLoginService socialLoginService;

    @Test
    void shouldLogin_OK() throws Exception {
        final var auth = buildAuth();

        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(auth);
        when(jwtTokenService.generateToken((JwtUserDetails) auth.getPrincipal()))
                .thenReturn("token");

        post(BASE_URL, auth)
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    void shouldLoginFacebookWithoutAccessToken_OK() {
        final JwtUserDetails userDetails = buildJwtUserDetails();
        final TokenRequestDTO tokenRequestDTO = TokenRequestDTO.builder().accessToken(randomAlphabetic(3, 10)).build();
        final String jwtToken = randomAlphabetic(3, 10);

        when(socialLoginService.loginWithFacebook(tokenRequestDTO.getAccessToken())).thenReturn(userDetails);
        when(jwtTokenService.generateToken(userDetails)).thenReturn(jwtToken);

        final var jwtTokenActual = jwtTokenService.generateToken(userDetails);

        assertEquals(jwtToken, jwtTokenActual);
        assertNotNull(tokenRequestDTO);
    }

    @Test
    void shouldLoginFacebookWithoutAccessToken_ThroughBadRequest() throws Exception {
        final JwtUserDetails userDetails = buildJwtUserDetails();
        final TokenRequestDTO tokenRequestDTO = TokenRequestDTO.builder().accessToken(randomAlphabetic(3, 10)).build();

        when(socialLoginService.loginWithFacebook(tokenRequestDTO.getAccessToken())).thenReturn(userDetails);

        post(BASE_URL + "/facebook", null).andExpect(status().isBadRequest());
    }

    @Test
    void shouldLoginGoogle_OK() throws Exception {
        final var tokenRequest = new TokenRequestDTO(randomAlphabetic(3, 10));
        final var token = randomAlphabetic(3, 10);
        final JwtUserDetails userDetails = buildJwtUserDetails();

        when(socialLoginService.loginWithGoogle(tokenRequest.getAccessToken()))
                .thenReturn(userDetails);
        when(jwtTokenService.generateToken(userDetails)).thenReturn(token);

        post(BASE_URL + "/google", tokenRequest)
                .andExpect(jsonPath("$.token").value(token));

        verify(socialLoginService).loginWithGoogle(tokenRequest.getAccessToken());
        verify(jwtTokenService).generateToken(userDetails);
    }
}

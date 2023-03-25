package com.bathanh.apibook.api.auth;

import com.bathanh.apibook.api.AbstractControllerTest;
import com.bathanh.apibook.domain.auths.JwtTokenService;
import com.bathanh.apibook.domain.auths.JwtUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static com.bathanh.apibook.fakes.AuthFakes.buildAuth;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/auths";

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

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
}

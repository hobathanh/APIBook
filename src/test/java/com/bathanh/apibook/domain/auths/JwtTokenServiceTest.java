package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {

    private static final String SECRET = "testSecret";
    private static final Long EXPIRATION = 3600L;
    private JwtTokenService jwtTokenService;

    @Mock
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtTokenService = new JwtTokenService(jwtProperties);
    }

    @Test
    public void generateToken_ShouldCreateValidToken() {
        final JwtUserDetails userDetails = new JwtUserDetails(
                null,
                "testUser",
                "testPassword",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(userDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        final String username = claims.getSubject();
        final Date expiration = claims.getExpiration();
        final Date issuedAt = claims.getIssuedAt();
        final String authority = claims.get("roles").toString();

        assertThat(username).isEqualTo("testUser");
        assertThat(authority).isEqualTo("ROLE_USER");
        assertThat(expiration).isAfter(issuedAt);
        assertThat(expiration.getTime() - issuedAt.getTime()).isEqualTo(EXPIRATION * 1000);
    }
}


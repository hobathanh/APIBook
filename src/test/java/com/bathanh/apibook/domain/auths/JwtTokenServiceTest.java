package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.bathanh.apibook.fakes.JwtFakes.buildJwtUserDetails;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class JwtTokenServiceTest {

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
    void generateToken_ShouldCreateValidToken() {
        final JwtUserDetails userDetails = buildJwtUserDetails("testUser", "testPassword", "ROLE_USER");

        when(jwtProperties.getSecret()).thenReturn(SECRET);
        when(jwtProperties.getExpiration()).thenReturn(EXPIRATION);

        final String token = jwtTokenService.generateToken(userDetails);
        final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        assertEquals("testUser", claims.getSubject());
        assertEquals("ROLE_USER", claims.get("roles").toString());
        assertTrue(claims.getExpiration().after(claims.getIssuedAt()));
        assertEquals(EXPIRATION * 1000, claims.getExpiration().getTime() - claims.getIssuedAt().getTime());
    }
}


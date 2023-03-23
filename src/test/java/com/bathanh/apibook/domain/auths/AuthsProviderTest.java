package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.error.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.bathanh.apibook.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthsProviderTest {

    @InjectMocks
    private AuthsProvider authsProvider;

    @Test
    void shouldGetCurrentAuthentication_OK() {
        final var expected = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(expected);

        final var actual = authsProvider.getCurrentAuthentication();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetCurrentAuthentication_Throw() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentAuthentication());
    }

    @Test
    void shouldGetCurrentUserId_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentUserId();

        assertEquals(user.getUserId(), actual);
    }

    @Test
    void shouldGetCurrentUserId_Throw() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentUserId());
    }

    @Test
    void shouldGetCurrentRole_OK() {
        final var user = buildAdmin();

        SecurityContextHolder.getContext().setAuthentication(user);

        final var actual = authsProvider.getCurrentUserRole();

        assertEquals(user.getRole(), actual);
    }

    @Test
    void shouldGetCurrentRole_Throw() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertThrows(UnauthorizedException.class, () -> authsProvider.getCurrentUserRole());
    }
}
package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.domain.role.Role;
import com.bathanh.apibook.domain.user.User;
import com.bathanh.apibook.persistence.role.RoleStore;
import com.bathanh.apibook.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static com.bathanh.apibook.domain.auths.UserDetailsMapper.toUserDetails;
import static com.bathanh.apibook.fakes.RoleFakes.buildRole;
import static com.bathanh.apibook.fakes.SocialTokenPayloadFakes.buildTokenSocial;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocialLoginServiceTest {

    @Mock
    private UserStore userStore;

    @Mock
    private GoogleTokenVerifierService googleTokenVerifierService;

    @Mock
    private FacebookTokenVerifierService facebookTokenVerifierService;

    @Mock
    private RoleStore roleStore;

    @InjectMocks
    private SocialLoginService socialLoginService;

    @Test
    void shouldLoginGoogle_OK() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final var user = buildUser()
                .withUsername(tokenPayload.getUsername());

        final var userDetails = toUserDetails(user, "CONTRIBUTOR");

        when(googleTokenVerifierService.verifyGoogleIdToken(anyString())).thenReturn(tokenPayload);
        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        final var actual = socialLoginService.loginWithGoogle(anyString());

        assertEquals(userDetails, actual);

        verify(googleTokenVerifierService).verifyGoogleIdToken(anyString());
        verify(userStore).findByUsername(user.getUsername());
    }

    @Test
    void shouldLoginGoogle_CreateNewUser() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final Role role = buildRole();
        final User newUser = buildUser()
                .withUsername(tokenPayload.getUsername())
                .withPassword(randomUUID().toString())
                .withFirstName(tokenPayload.getFirstName())
                .withLastName(tokenPayload.getLastName())
                .withEnabled(true)
                .withRoleId(role.getId());

        when(googleTokenVerifierService.verifyGoogleIdToken(anyString())).thenReturn(tokenPayload);
        when(userStore.findByUsername(tokenPayload.getUsername())).thenReturn(Optional.empty());
        when(roleStore.findByName(anyString())).thenReturn(role);
        when(userStore.create(any())).thenReturn(newUser);

        final JwtUserDetails userDetails = new JwtUserDetails(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getPassword(),
                List.of(new SimpleGrantedAuthority("CONTRIBUTOR")));

        final var actual = socialLoginService.loginWithGoogle(anyString());

        assertEquals(userDetails, actual);

        verify(googleTokenVerifierService).verifyGoogleIdToken(anyString());
        verify(userStore).findByUsername(tokenPayload.getUsername());
        verify(roleStore).findByName(anyString());
        verify(userStore).create(any());
    }

    @Test
    void shouldLoginFacebook_OK() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final var user = buildUser();
        final var role = buildRole();

        user.setUsername(tokenPayload.getUsername());

        final UserDetails userDetails = toUserDetails(user, role.getName());

        when(facebookTokenVerifierService.verifyFacebookIdToken(anyString())).thenReturn(tokenPayload);
        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        final var actual = socialLoginService.loginWithFacebook(anyString());

        assertEquals(userDetails, actual);

        verify(facebookTokenVerifierService).verifyFacebookIdToken(anyString());
        verify(userStore).findByUsername(user.getUsername());
    }

    @Test
    void shouldLoginFacebook_CreateNewUser() {
        final SocialTokenPayload tokenPayload = buildTokenSocial();
        final Role role = buildRole();
        final User newUser = buildUser()
                .withUsername(tokenPayload.getUsername())
                .withPassword(randomUUID().toString())
                .withFirstName(tokenPayload.getFirstName())
                .withLastName(tokenPayload.getLastName())
                .withEnabled(true)
                .withRoleId(role.getId());

        when(facebookTokenVerifierService.verifyFacebookIdToken(anyString())).thenReturn(tokenPayload);
        when(userStore.findByUsername(tokenPayload.getUsername())).thenReturn(Optional.empty());
        when(roleStore.findByName(anyString())).thenReturn(role);
        when(userStore.create(any())).thenReturn(newUser);

        final var userDetails = toUserDetails(newUser, role.getName());
        final var actual = socialLoginService.loginWithFacebook(anyString());

        assertEquals(userDetails, actual);

        verify(facebookTokenVerifierService).verifyFacebookIdToken(anyString());
        verify(userStore).findByUsername(tokenPayload.getUsername());
        verify(roleStore).findByName(anyString());
        verify(userStore).create(any());
    }
}
package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.domain.user.User;
import com.bathanh.apibook.persistence.role.RoleStore;
import com.bathanh.apibook.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.bathanh.apibook.domain.auths.UserDetailsMapper.toUserDetails;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final GoogleTokenVerifierService googleTokenVerifierService;

    private final FacebookTokenVerifierService facebookTokenVerifierService;

    private final UserStore userStore;

    private final RoleStore roleStore;

    public UserDetails loginWithGoogle(final String tokenAccess) {
        final SocialTokenPayload googleAccount = googleTokenVerifierService.verifyGoogleIdToken(tokenAccess);

        return getJwtUserDetails(googleAccount);
    }

    public UserDetails loginWithFacebook(final String tokenAccess) {
        final SocialTokenPayload facebookAccount = facebookTokenVerifierService.verifyFacebookIdToken(tokenAccess);

        return getJwtUserDetails(facebookAccount);
    }

    private UserDetails getJwtUserDetails(final SocialTokenPayload socialTokenPayload) {
        return userStore.findByUsername(socialTokenPayload.getUsername())
                .map(user -> toUserDetails(user, "CONTRIBUTOR"))
                .orElseGet(() -> {
                    final User user = createNewUser(socialTokenPayload);

                    return toUserDetails(user, "CONTRIBUTOR");
                });
    }

    private User createNewUser(final SocialTokenPayload socialTokenPayload) {
        final var contributorRoleId = roleStore.findByName("CONTRIBUTOR").getId();
        final User user = User.builder()
                .username(socialTokenPayload.getUsername())
                .password(randomUUID().toString())
                .firstName(socialTokenPayload.getFirstName())
                .lastName(socialTokenPayload.getLastName())
                .enabled(true)
                .roleId(contributorRoleId)
                .build();

        return userStore.create(user);
    }
}

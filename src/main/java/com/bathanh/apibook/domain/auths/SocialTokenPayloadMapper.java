package com.bathanh.apibook.domain.auths;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.experimental.UtilityClass;
import org.springframework.social.facebook.api.User;

import static java.lang.String.valueOf;

@UtilityClass
public class SocialTokenPayloadMapper {

    public static SocialTokenPayload toSocialTokenPayloadFromGoogle(final GoogleIdToken.Payload payload) {
        return SocialTokenPayload.builder()
                .username(payload.getEmail())
                .firstName(valueOf(payload.get("family_name")))
                .lastName(valueOf(payload.get("given_name")))
                .build();
    }

    public static SocialTokenPayload toSocialTokenPayloadFromFacebook(final User user) {
        return SocialTokenPayload.builder()
                .username(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}


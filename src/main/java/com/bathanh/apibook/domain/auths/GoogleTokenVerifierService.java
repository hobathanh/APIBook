package com.bathanh.apibook.domain.auths;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bathanh.apibook.domain.auths.SocialTokenPayloadMapper.toSocialTokenPayloadFromGoogle;

@Service
@RequiredArgsConstructor
public class GoogleTokenVerifierService {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public SocialTokenPayload verifyGoogleIdToken(final String tokenId) {
        try {
            final var googleIdToken = googleIdTokenVerifier.verify(tokenId);
            final GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return toSocialTokenPayloadFromGoogle(payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

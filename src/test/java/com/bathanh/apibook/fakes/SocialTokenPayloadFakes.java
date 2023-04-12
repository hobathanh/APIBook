package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.auths.SocialTokenPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocialTokenPayloadFakes {

    public static SocialTokenPayload buildTokenSocial() {
        return SocialTokenPayload.builder()
                .username("hobathanh")
                .firstName("ho")
                .lastName("thanh")
                .build();
    }
}


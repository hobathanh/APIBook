package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.user.SocialUser;
import lombok.experimental.UtilityClass;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class SocialUserFakes {

    public static SocialUser buildSocialUser() {
        return SocialUser.builder()
                .id(randomAlphabetic(6, 10))
                .firstName(randomAlphabetic(6, 10))
                .lastName(randomAlphabetic(6, 10))
                .build();
    }
}

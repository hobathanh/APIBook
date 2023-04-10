package com.bathanh.apibook.api.user;

import com.bathanh.apibook.domain.user.SocialUser;
import com.bathanh.apibook.domain.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocialUserMapper {

    public static SocialUser toSocialUser(final User user) {
        return SocialUser.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
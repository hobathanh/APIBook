package com.bathanh.apibook.api.user;

import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.api.user.SocialUserMapper.toSocialUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SocialUserMapperTest {

    @Test
    void shouldToSocialUser_OK() {
        final var user = buildUser();
        final var socialUser = toSocialUser(user);

        assertEquals(user.getUsername(), socialUser.getUsername());
        assertEquals(user.getFirstName(), socialUser.getFirstName());
        assertEquals(user.getLastName(), socialUser.getLastName());
    }
}
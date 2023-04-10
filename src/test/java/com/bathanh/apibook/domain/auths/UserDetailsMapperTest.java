package com.bathanh.apibook.domain.auths;

import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.domain.auths.UserDetailsMapper.toUserDetails;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDetailsMapperTest {

    @Test
    void shouldToUserDetails_OK() {
        final var user = buildUser();
        final var userDetail = toUserDetails(user, "CONTRIBUTOR");

        assertEquals(user.getUsername(), userDetail.getUsername());
        assertEquals(user.getPassword(), userDetail.getPassword());
    }

    @Test
    void shouldToUserDetailsWithUserNull_OK() {
        assertNull(toUserDetails(null, "CONTRIBUTOR"));
    }
}
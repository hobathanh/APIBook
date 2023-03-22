package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.auths.JwtUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class AuthFakes {

    public static Authentication buildAuth() {
        final JwtUserDetails jwtUserDetails = new JwtUserDetails(
                UUID.randomUUID(),
                randomAlphabetic(3, 10),
                randomAlphabetic(3, 10),
                Collections.emptyList()
        );

        return new UsernamePasswordAuthenticationToken(
                jwtUserDetails,
                jwtUserDetails.getPassword(),
                jwtUserDetails.getAuthorities());
    }
}

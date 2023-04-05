package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.auths.JwtUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static java.util.UUID.randomUUID;

@UtilityClass
public class JwtFakes {

    public static JwtUserDetails buildJwtUserDetails() {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new JwtUserDetails(randomUUID(), "testUser", "testPassword", authorities);
    }

    public static JwtUserDetails buildJwtUserDetail_NullUserId() {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new JwtUserDetails(null, "testUser", "testPassword", authorities);
    }
}

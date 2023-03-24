package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.auths.JwtUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class JwtFakes {

    public static JwtUserDetails buildJwtUserDetails(String username, String password, String role) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return new JwtUserDetails(null, username, password, authorities);
    }
}

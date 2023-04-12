package com.bathanh.apibook.domain.auths;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@UtilityClass
public class UserDetailsMapper {

    public static UserDetails toUserDetails(final com.bathanh.apibook.domain.user.User user, final String roleName) {
        if (user == null) {
            return null;
        }

        return new JwtUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(roleName))
        );
    }
}

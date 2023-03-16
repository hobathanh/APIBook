package com.bathanh.apibook.domain.auths;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;


@Getter
public class JwtUserDetails extends User {

    private final UUID userId;

    public JwtUserDetails(final UUID userId,
                          final String username,
                          final String password,
                          final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }
}

package com.bathanh.apibook.domain.auths;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

import static com.bathanh.apibook.error.CommonError.supplyForbiddenError;

@Getter
public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final UUID userId;
    private final String username;
    private final String role;

    public UserAuthenticationToken(final UUID userId, final String username,
                                   final Collection<? extends GrantedAuthority> authorities) {
        super(userId, username, authorities);
        this.userId = userId;
        this.username = username;
        this.role = this.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(supplyForbiddenError("You do not have permission to access this resource"));
    }
}

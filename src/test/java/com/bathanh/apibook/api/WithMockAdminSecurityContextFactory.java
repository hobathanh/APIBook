package com.bathanh.apibook.api;

import com.bathanh.apibook.domain.auths.UserAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.UUID;

public class WithMockAdminSecurityContextFactory implements WithSecurityContextFactory<WithMockAdmin> {

    @Override
    public SecurityContext createSecurityContext(WithMockAdmin annotation) {
        final var context = SecurityContextHolder.createEmptyContext();

        final Authentication auth = new UserAuthenticationToken(
                UUID.fromString("12345678-b624-4786-a720-a1cbcc255383"),
                "admin",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        context.setAuthentication(auth);
        return context;
    }
}
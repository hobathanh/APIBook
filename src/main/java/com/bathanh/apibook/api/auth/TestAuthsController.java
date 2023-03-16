package com.bathanh.apibook.api.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test-auths")
@RequiredArgsConstructor
public class TestAuthsController {

    private final AuthsProvider authsProvider;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("users")
    public UserProfileDTO testUser() {
        return toUserProfileDTO(authsProvider.getCurrentAuthentication());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("admin")
    public UserProfileDTO testAdmin() {
        return toUserProfileDTO(authsProvider.getCurrentAuthentication());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("all")
    public UserProfileDTO testAll() {
        return toUserProfileDTO(authsProvider.getCurrentAuthentication());
    }
}

package com.bathanh.apibook.api.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class UserProfileDTO {

    private UUID userId;
    private String username;
    private String role;
}

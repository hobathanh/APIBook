package com.bathanh.apibook.api.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponseDTO {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String avatar;
    private UUID roleId;
}
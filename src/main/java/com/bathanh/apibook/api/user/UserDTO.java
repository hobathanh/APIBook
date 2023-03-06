package com.bathanh.apibook.api.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserDTO {

    private UUID id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean enabled;
    private String avatar;
    private UUID roleId;
}

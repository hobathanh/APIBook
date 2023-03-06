package com.bathanh.apibook.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class User {

    private UUID id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean enabled;
    private String avatar;
    private UUID roleId;
}

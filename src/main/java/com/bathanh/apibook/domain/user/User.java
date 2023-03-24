package com.bathanh.apibook.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.util.UUID;

@Builder
@Getter
@Setter
@With
public class User {

    private UUID id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String avatar;
    private UUID roleId;
}

package com.bathanh.apibook.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SocialUser {

    private String firstName;

    private String lastName;

    private String username;
}

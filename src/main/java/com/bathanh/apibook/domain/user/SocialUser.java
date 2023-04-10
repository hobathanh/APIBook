package com.bathanh.apibook.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SocialUser {

    private String firstName;

    private String lastName;

    private String username;
}

package com.bathanh.apibook.domain.auths;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SocialTokenPayload {

    private String username;

    private String firstName;

    private String lastName;
}
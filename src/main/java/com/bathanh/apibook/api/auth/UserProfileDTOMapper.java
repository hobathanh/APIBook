package com.bathanh.apibook.api.auth;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserProfileDTOMapper {

    public static UserProfileDTO toUserProfileDTO(final UserAuthenticationToken userAuthenticationToken) {
        return UserProfileDTO.builder()
                .userId(userAuthenticationToken.getUserId())
                .username(userAuthenticationToken.getUsername())
                .role(userAuthenticationToken.getRole())
                .build();
    }
}

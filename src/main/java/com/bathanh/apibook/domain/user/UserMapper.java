package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.api.user.UserRequestDTO;

public class UserMapper {

    public static User toUser(final UserRequestDTO userRequestDTO) {
        return User.builder()
                .id(userRequestDTO.getId())
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .avatar(userRequestDTO.getAvatar())
                .enabled(userRequestDTO.isEnabled())
                .roleId(userRequestDTO.getRoleId())
                .build();
    }
}

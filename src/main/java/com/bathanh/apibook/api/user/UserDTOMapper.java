package com.bathanh.apibook.api.user;

import com.bathanh.apibook.domain.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class UserDTOMapper {

    public static UserDTO toUserDTO(final User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .enabled(user.isEnabled())
                .avatar(user.getAvatar())
                .roleId(user.getRoleId())
                .build();
    }

    public static User toUser(final UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .enabled(userDTO.isEnabled())
                .avatar(userDTO.getAvatar())
                .roleId(userDTO.getRoleId())
                .build();
    }

    public static List<UserDTO> toUserDTOs(final List<User> users) {
        return emptyIfNull(users)
                .stream()
                .map(UserDTOMapper::toUserDTO)
                .toList();
    }
}

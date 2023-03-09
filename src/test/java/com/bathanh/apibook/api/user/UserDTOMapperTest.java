package com.bathanh.apibook.api.user;

import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTO;
import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTOs;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOMapperTest {

    @Test
    void toUserDTO_OK() {
        final var user = buildUser();
        final var userDTO = toUserDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.isEnabled(), userDTO.isEnabled());
        assertEquals(user.getAvatar(), userDTO.getAvatar());
        assertEquals(user.getRoleId(), userDTO.getRoleId());
    }

    @Test
    void toUserDTOs_OK() {
        final var users = buildUsers();

        final var userDTOs = toUserDTOs(users);
        assertEquals(users.size(), userDTOs.size());
    }
}
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
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getFirstname(), userDTO.getFirstname());
        assertEquals(user.getLastname(), userDTO.getLastname());
        assertEquals(user.isEnabled(), userDTO.isEnabled());
        assertEquals(user.getAvatar(), userDTO.getAvatar());
        assertEquals(user.getRoleId(), userDTO.getRoleId());
    }

    @Test
    void toUser_OK() {
        final var user = buildUser();
        final var userDTO = toUserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getFirstname(), user.getFirstname());
        assertEquals(userDTO.getLastname(), user.getLastname());
        assertEquals(userDTO.isEnabled(), user.isEnabled());
        assertEquals(userDTO.getAvatar(), user.getAvatar());
        assertEquals(userDTO.getRoleId(), user.getRoleId());
    }

    @Test
    void toUserDTOs_OK() {
        final var users = buildUsers();

        final var userDTOs = toUserDTOs(users);
        assertEquals(users.size(), userDTOs.size());
    }
}
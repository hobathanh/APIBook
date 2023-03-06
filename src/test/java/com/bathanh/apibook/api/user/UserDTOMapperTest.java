package com.bathanh.apibook.api.user;

import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTO;
import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTOs;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOMapperTest {

    @Test
    void toUserDTO_Ok() {
        final var user = buildUser();
        final var userDTO = toUserDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.isEnabled(), userDTO.isEnabled());
        assertEquals(user.getAvatar(), userDTO.getAvatar());
        assertEquals(user.getRoleId(), userDTO.getRoleId());
    }

    @Test
    void toUser_Ok() {
        final var user = buildUser();
        final var userDTO = toUserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.isEnabled(), user.isEnabled());
        assertEquals(userDTO.getAvatar(), user.getAvatar());
        assertEquals(userDTO.getRoleId(), user.getRoleId());
    }

    @Test
    void toUserDTOs_Ok() {
        final var users = buildUsers();

        final var userDTOs = toUserDTOs(users);
        assertEquals(users.size(), userDTOs.size());
    }
}
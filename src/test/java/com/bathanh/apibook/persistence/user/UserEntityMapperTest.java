package com.bathanh.apibook.persistence.user;

import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.fakes.UserFakes.buildUserEntities;
import static com.bathanh.apibook.fakes.UserFakes.buildUserEntity;
import static com.bathanh.apibook.persistence.user.UserEntityMapper.toUser;
import static com.bathanh.apibook.persistence.user.UserEntityMapper.toUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEntityMapperTest {

    @Test
    void toUser_OK() {
        final var userEntity = buildUserEntity();
        final var user = toUser(userEntity);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getPassword(), user.getPassword());
        assertEquals(userEntity.getFirstname(), user.getFirstname());
        assertEquals(userEntity.getLastname(), user.getLastname());
        assertEquals(userEntity.isEnabled(), user.isEnabled());
        assertEquals(userEntity.getAvatar(), user.getAvatar());
        assertEquals(userEntity.getRoleId(), user.getRoleId());
    }

    @Test
    void toUserEntity() {
        final var userEntity = buildUserEntity();
        final var user = toUser(userEntity);

        assertEquals(user.getId(), userEntity.getId());
        assertEquals(user.getUsername(), userEntity.getUsername());
        assertEquals(user.getPassword(), userEntity.getPassword());
        assertEquals(user.getFirstname(), userEntity.getFirstname());
        assertEquals(user.getLastname(), userEntity.getLastname());
        assertEquals(user.isEnabled(), userEntity.isEnabled());
        assertEquals(user.getAvatar(), userEntity.getAvatar());
        assertEquals(user.getRoleId(), userEntity.getRoleId());
    }

    @Test
    void toUsers_OK() {
        final var userEntities = buildUserEntities();
        final var user = toUsers(userEntities);

        assertEquals(userEntities.size(), user.size());
    }
}
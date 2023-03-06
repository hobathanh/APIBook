package com.bathanh.apibook.persistence.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.fakes.UserFakes.*;
import static com.bathanh.apibook.persistence.user.UserEntityMapper.toUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStoreTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserStore userStore;

    @Test
    void findAllUsers_Ok() {
        final var expected = buildUserEntities();

        when(userRepository.findAll())
                .thenReturn(expected);

        assertEquals(expected.size(), userStore.findAllUsers().size());

        verify(userRepository).findAll();
    }

    @Test
    void findUserById_Ok() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);
        when(userRepository.findById(user.getId()))
                .thenReturn(userOpt);

        assertEquals(userOpt, userRepository.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }

    @Test
    void findUserByUserName_Ok() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(userOpt);

        final var actual = userStore.findUserByUserName(user.getUsername()).get();
        final var expected = userOpt.get();

        assertEquals(expected.getId().toString(), actual.getId().toString());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getRoleId().toString(), actual.getRoleId().toString());

        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void createUser_Ok() {
        final var user = buildUser();
        final var userEntity = buildUserEntity();

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(userEntity);

        final var actual = userStore.createUser(user);

        assertEquals(userEntity.getId().toString(), actual.getId().toString());
        assertEquals(userEntity.getUsername(), actual.getUsername());
        assertEquals(userEntity.getPassword(), actual.getPassword());
        assertEquals(userEntity.getFirstName(), actual.getFirstName());
        assertEquals(userEntity.getLastName(), actual.getLastName());
        assertEquals(userEntity.getAvatar(), actual.getAvatar());
        assertEquals(userEntity.isEnabled(), actual.isEnabled());
        assertEquals(userEntity.getRoleId().toString(), actual.getRoleId().toString());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUser_Ok() {
        final var userUpdate = buildUserEntity();

        when(userRepository.save(any())).thenReturn(userUpdate);

        final var actual = userStore.updateUser(toUser(userUpdate));

        assertEquals(userUpdate.getId().toString(), actual.getId().toString());
        assertEquals(userUpdate.getUsername(), actual.getUsername());
        assertEquals(userUpdate.getPassword(), actual.getPassword());
        assertEquals(userUpdate.getFirstName(), actual.getFirstName());
        assertEquals(userUpdate.getLastName(), actual.getLastName());
        assertEquals(userUpdate.getAvatar(), actual.getAvatar());
        assertEquals(userUpdate.isEnabled(), actual.isEnabled());
        assertEquals(userUpdate.getRoleId().toString(), actual.getRoleId().toString());
    }

    @Test
    void deleteUser_Ok() {
        final var userId = UUID.randomUUID();

        userStore.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
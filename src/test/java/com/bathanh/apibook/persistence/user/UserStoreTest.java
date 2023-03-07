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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStoreTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserStore userStore;

    @Test
    void findAllUsers_OK() {
        final var expected = buildUserEntities();

        when(userRepository.findAll())
                .thenReturn(expected);

        assertEquals(expected.size(), userStore.findAllUsers().size());

        verify(userRepository).findAll();
    }

    @Test
    void searchUsers_OK() {
        final var expected = buildUserEntities();
        final var user = buildUserEntity();

        when(userRepository.findAllByFirstnameContainingOrLastnameContainingOrUsernameContaining(anyString(), anyString(), anyString()))
                .thenReturn(expected);

        final var actual = userStore.searchUsers(user.getUsername());

        assertEquals(expected.size(), actual.size());

        verify(userRepository).findAllByFirstnameContainingOrLastnameContainingOrUsernameContaining(anyString(), anyString(), anyString());
    }

    @Test
    void findUserById_OK() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);
        when(userRepository.findById(user.getId()))
                .thenReturn(userOpt);

        assertEquals(userOpt, userRepository.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }

    @Test
    void findUserByUserName_OK() {
        final var user = buildUserEntity();
        final var userOpt = Optional.of(user);

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(userOpt);

        final var actual = userStore.findUserByUsername(user.getUsername()).get();
        final var expected = userOpt.get();

        assertEquals(expected.getId().toString(), actual.getId().toString());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getAvatar(), actual.getAvatar());
        assertEquals(expected.isEnabled(), actual.isEnabled());
        assertEquals(expected.getRoleId(), actual.getRoleId());

        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void createUser_OK() {
        final var user = buildUser();
        final var userEntity = buildUserEntity();

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(userEntity);

        final var actual = userStore.createUser(user);

        assertEquals(userEntity.getId().toString(), actual.getId().toString());
        assertEquals(userEntity.getUsername(), actual.getUsername());
        assertEquals(userEntity.getPassword(), actual.getPassword());
        assertEquals(userEntity.getFirstname(), actual.getFirstname());
        assertEquals(userEntity.getLastname(), actual.getLastname());
        assertEquals(userEntity.getAvatar(), actual.getAvatar());
        assertEquals(userEntity.isEnabled(), actual.isEnabled());
        assertEquals(userEntity.getRoleId(), actual.getRoleId());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUser_OK() {
        final var userUpdate = buildUserEntity();

        when(userRepository.save(any())).thenReturn(userUpdate);

        final var actual = userStore.updateUser(toUser(userUpdate));

        assertEquals(userUpdate.getId().toString(), actual.getId().toString());
        assertEquals(userUpdate.getUsername(), actual.getUsername());
        assertEquals(userUpdate.getPassword(), actual.getPassword());
        assertEquals(userUpdate.getFirstname(), actual.getFirstname());
        assertEquals(userUpdate.getLastname(), actual.getLastname());
        assertEquals(userUpdate.getAvatar(), actual.getAvatar());
        assertEquals(userUpdate.isEnabled(), actual.isEnabled());
        assertEquals(userUpdate.getRoleId(), actual.getRoleId());
    }

    @Test
    void deleteUser_OK() {
        final var userId = UUID.randomUUID();

        userStore.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
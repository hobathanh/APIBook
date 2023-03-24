package com.bathanh.apibook.persistence.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

        final var actual = userStore.findAll();

        assertEquals(expected.size(), actual.size());

        verify(userRepository).findAll();
    }

    @Test
    void searchUsers_OK() {
        final var expected = buildUserEntities();
        final var user = buildUserEntity();

        when(userRepository.findAllByFirstNameOrLastNameOrUsername(anyString()))
                .thenReturn(expected);

        final var actual = userStore.search(user.getUsername());

        assertEquals(expected.size(), actual.size());

        verify(userRepository).findAllByFirstNameOrLastNameOrUsername(anyString());
    }

    @Test
    void findUserById_OK() {
        final var user = buildUserEntity();
        final var userOptional = Optional.of(user);
        when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        assertEquals(userOptional, userRepository.findById(user.getId()));

        verify(userRepository).findById(user.getId());
    }

    @Test
    void findUserByUserName_OK() {
        final var user = buildUserEntity();
        final var userOptional = Optional.of(user);

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(userOptional);

        final var actual = userStore.findByUsername(user.getUsername()).get();
        final var expected = userOptional.get();

        assertEquals(expected.getId().toString(), actual.getId().toString());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
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

        final var actual = userStore.create(user);

        assertEquals(userEntity.getId().toString(), actual.getId().toString());
        assertEquals(userEntity.getUsername(), actual.getUsername());
        assertEquals(userEntity.getPassword(), actual.getPassword());
        assertEquals(userEntity.getFirstName(), actual.getFirstName());
        assertEquals(userEntity.getLastName(), actual.getLastName());
        assertEquals(userEntity.getAvatar(), actual.getAvatar());
        assertEquals(userEntity.isEnabled(), actual.isEnabled());
        assertEquals(userEntity.getRoleId(), actual.getRoleId());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUser_OK() {
        final var userUpdate = buildUserEntity();

        when(userRepository.save(any())).thenReturn(userUpdate);

        final var actual = userStore.update(toUser(userUpdate));

        assertEquals(userUpdate.getId().toString(), actual.getId().toString());
        assertEquals(userUpdate.getUsername(), actual.getUsername());
        assertEquals(userUpdate.getPassword(), actual.getPassword());
        assertEquals(userUpdate.getFirstName(), actual.getFirstName());
        assertEquals(userUpdate.getLastName(), actual.getLastName());
        assertEquals(userUpdate.getAvatar(), actual.getAvatar());
        assertEquals(userUpdate.isEnabled(), actual.isEnabled());
        assertEquals(userUpdate.getRoleId(), actual.getRoleId());
    }

    @Test
    void deleteUser_OK() {
        final var user = buildUserEntity();
        userStore.delete(user.getId());

        verify(userRepository).deleteById(user.getId());
    }
}
package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.NotFoundException;
import com.bathanh.apibook.error.UserAlreadyExistException;
import com.bathanh.apibook.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserStore userStore;

    @InjectMocks
    private UserService userService;

    @Test
    void findAllUsers_OK() {
        final var expected = buildUsers();

        when(userStore.findAll()).thenReturn(expected);

        final var actual = userService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getPassword(), actual.get(0).getPassword());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).isEnabled(), actual.get(0).isEnabled());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());

        verify(userStore).findAll();
    }

    @Test
    void searchUsers_OK() {
        final var expected = buildUsers();

        when(userStore.search(anyString())).thenReturn(expected);

        assertEquals(expected, userService.search(anyString()));
        verify(userStore).search(anyString());
    }

    @Test
    void findUserById_OK() {
        final var expected = buildUser();

        when(userStore.findById((expected.getId())))
                .thenReturn(Optional.of(expected));

        assertEquals(expected, userService.findById(expected.getId()));
        verify(userStore).findById(expected.getId());
    }

    @Test
    void findUserById_ThrowNotFound() {
        final var id = randomUUID();

        when(userStore.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(id));
        verify(userStore).findById(id);
    }

    @Test
    void createUser_OK() {
        final var user = buildUser();

        when(userStore.create(user)).thenReturn(user);

        final var result = userService.create(user);

        assertEquals(user, result);
        verify(userStore).create(user);
    }

    @Test
    void createUser_ThrowUserAlreadyExist() {
        final var user = buildUser();

        when(userStore.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class, () -> userService.create(user));
        verify(userStore).findByUsername(user.getUsername());
        verify(userStore, never()).create(user);
    }

    @Test
    void updateUser_OK() {
        final var user = buildUser();
        final var updatedUser = buildUser();

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStore.update(user)).thenReturn(updatedUser);

        final var actual = userService.update(user.getId(), updatedUser);

        assertEquals(updatedUser.getId(), actual.getId());
        assertEquals(updatedUser.getUsername(), actual.getUsername());
        assertEquals(updatedUser.getPassword(), actual.getPassword());
        assertEquals(updatedUser.getFirstName(), actual.getFirstName());
        assertEquals(updatedUser.getLastName(), actual.getLastName());
        assertEquals(updatedUser.isEnabled(), actual.isEnabled());
        assertEquals(updatedUser.getAvatar(), actual.getAvatar());
        assertEquals(updatedUser.getRoleId(), actual.getRoleId());

        verify(userStore).findById(user.getId());
        verify(userStore).update(user);
    }

    @Test
    void deleteUser_OK() {
        final var user = buildUser();

        when(userStore.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.delete(user.getId());

        verify(userStore).findById(user.getId());
        verify(userStore).delete(user.getId());
    }

    @Test
    void deleteUser_ThrowNotFound() {
        final var id = randomUUID();

        when(userStore.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(id));
        verify(userStore).findById(id);
    }
}
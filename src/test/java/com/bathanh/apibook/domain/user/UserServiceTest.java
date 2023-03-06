package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.NotFoundException;
import com.bathanh.apibook.error.UserAvailableException;
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
    void findAllUsers_Ok() {
        final var expected = buildUsers();

        when(userStore.findAllUsers())
                .thenReturn(expected);

        final var actual = userService.findAllUsers();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getPassword(), actual.get(0).getPassword());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).isEnabled(), actual.get(0).isEnabled());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());

        verify(userStore).findAllUsers();
    }

    @Test
    void findUserById_Ok() {
        final var expected = buildUser();
        when(userStore.findUserById((expected.getId())))
                .thenReturn(Optional.of(expected));

        assertEquals(expected, userService.findUserById(expected.getId()));
        verify(userStore).findUserById(expected.getId());
    }

    @Test
    void findUserById_Throw() {
        final var id = randomUUID();
        when(userStore.findUserById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findUserById(id));
        verify(userStore).findUserById(id);
    }

    @Test
    void createUser_Ok() {
        final var user = buildUser();

        when(userStore.findUserByUserName(user.getUsername())).thenReturn(Optional.empty());
        when(userStore.createUser(user)).thenReturn(user);

        final var result = userService.createUser(user);

        assertEquals(user, result);
        verify(userStore).findUserByUserName(user.getUsername());
        verify(userStore).createUser(user);
    }


    @Test
    void createUser_WithExistingUser() {
        final var user = buildUser();

        when(userStore.findUserByUserName(user.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UserAvailableException.class, () -> userService.createUser(user));
        verify(userStore).findUserByUserName(user.getUsername());
        verify(userStore, never()).createUser(user);
    }

    @Test
    void updateUser_Ok() {
        final var user = buildUser();
        final var updatedUser = buildUser();

        when(userStore.findUserById(user.getId()))
                .thenReturn(Optional.of(user));
        when(userStore.updateUser(user))
                .thenReturn(updatedUser);

        final var actual = userService.updateUser(user.getId(), updatedUser);

        assertEquals(updatedUser.getId(), actual.getId());
        assertEquals(updatedUser.getUsername(), actual.getUsername());
        assertEquals(updatedUser.getPassword(), actual.getPassword());
        assertEquals(updatedUser.getFirstName(), actual.getFirstName());
        assertEquals(updatedUser.getLastName(), actual.getLastName());
        assertEquals(updatedUser.isEnabled(), actual.isEnabled());
        assertEquals(updatedUser.getAvatar(), actual.getAvatar());
        assertEquals(updatedUser.getRoleId(), actual.getRoleId());

        verify(userStore).findUserById(user.getId());
        verify(userStore).updateUser(user);
    }

    @Test
    void deleteUser_Ok() {
        final var user = buildUser();

        when(userStore.findUserById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        verify(userStore).findUserById(user.getId());
        verify(userStore).deleteUser(user.getId());
    }

    @Test
    void deleteUser_Throw() {
        final var id = randomUUID();
        when(userStore.findUserById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(id));
        verify(userStore).findUserById(id);
    }
}
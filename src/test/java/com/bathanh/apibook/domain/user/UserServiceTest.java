package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.error.NotFoundException;
import com.bathanh.apibook.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
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
    void shouldFindAll_OK() {
        final var expected = buildUsers();

        when(userStore.findAll()).thenReturn(expected);

        final var actual = userService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).isEnabled(), actual.get(0).isEnabled());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());

        verify(userStore).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var expected = buildUser();

        when(userStore.findById(expected.getId())).thenReturn(Optional.of(expected));

        final var actual = userService.findById(expected.getId());

        assertEquals(expected, actual);

        verify(userStore).findById(expected.getId());
    }

    @Test
    void shouldFindById_ThrowNotFoundException() {
        final var uuid = randomUUID();

        when(userStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(uuid));

        verify(userStore).findById(uuid);
    }

    @Test
    void shouldSearch_OK() {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userStore.search(user.getUsername())).thenReturn(expected);

        final var actual = userService.search(user.getUsername());

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
        assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        assertEquals(expected.get(0).isEnabled(), actual.get(0).isEnabled());
        assertEquals(expected.get(0).getRoleId(), actual.get(0).getRoleId());
        assertEquals(expected.get(0).getAvatar(), actual.get(0).getAvatar());
    }

    @Test
    void shouldCreate_OK() {
        final var user = buildUser()
                .withPassword(randomAlphabetic(6, 10));

        when(userStore.create(user)).thenReturn(user);

        final var userCreated = userService.create(user);

        assertEquals(user, userCreated);

        verify(userStore).create(user);
    }

    @Test
    void shouldCreate_ThrownBadRequestException() {
        final var user = buildUser()
                .withPassword(null)
                .withUsername(null);

        assertThrows(BadRequestException.class, () -> userService.create(user));
    }

    @Test
    void shouldCreate_ThrownUsernameAlreadyExist() {
        final var user = buildUser();

        when(userStore.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> userService.create(user));

        verify(userStore).findByUsername(user.getUsername());
        verify(userStore, never()).create(user);
    }

    @Test
    void shouldUpdate_OK() {
        final var user = buildUser();
        final var userUpdate = buildUser()
                .withId(user.getId())
                .withRoleId(user.getRoleId())
                .withPassword(randomAlphabetic(6, 10));

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStore.update(user)).thenReturn(user);

        final var actual = userService.update(user.getId(), userUpdate);

        assertEquals(userUpdate.getId().toString(), actual.getId().toString());
        assertEquals(userUpdate.getUsername(), actual.getUsername());
        assertEquals(userUpdate.getFirstName(), actual.getFirstName());
        assertEquals(userUpdate.getLastName(), actual.getLastName());
        assertEquals(userUpdate.getAvatar(), actual.getAvatar());
        assertEquals(userUpdate.getRoleId().toString(), actual.getRoleId().toString());
        assertEquals(userUpdate.isEnabled(), actual.isEnabled());

        verify(userStore).update(user);
    }

    @Test
    void shouldUpdate_ThrownLengthPasswordException() {
        final var user = buildUser();
        final var userUpdate = buildUser()
                .withPassword(randomAlphabetic(3, 5));

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> userService.update(user.getId(), userUpdate));

        verify(userStore, never()).update(userUpdate);
    }

    @Test
    void shouldUpdate_ThrownNotFoundException() {
        final var uuid = randomUUID();
        final var userUpdate = buildUser();

        when(userStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(uuid, userUpdate));

        verify(userStore, never()).update(userUpdate);
    }

    @Test
    void shouldUpdate_ThrownUsernameAlreadyExist() {
        final var userToUpdate = buildUser();
        final var userExisted = buildUser();
        final var userUpdate = buildUser()
                .withUsername(userExisted.getUsername());

        when(userStore.findById(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));
        when(userStore.findByUsername(userUpdate.getUsername())).thenReturn(Optional.of(userUpdate));

        assertThrows(BadRequestException.class, () -> userService.update(userToUpdate.getId(), userUpdate));

        verify(userStore).findByUsername(userExisted.getUsername());
        verify(userStore, never()).findById(randomUUID());
        verify(userStore, never()).update(userUpdate);
    }

    @Test
    void shouldDelete_OK() {
        final var user = buildUser();

        when(userStore.findById(user.getId())).thenReturn(Optional.of(user));

        userService.delete(user.getId());

        verify(userStore).delete(user.getId());
    }

    @Test
    void shouldDelete_ThrownNotFoundException() {
        final var uuid = UUID.randomUUID();

        when(userStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(uuid));

        verify(userStore).findById(uuid);
    }
}
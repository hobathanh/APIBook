package com.bathanh.apibook.api.user;

import com.bathanh.apibook.api.AbstractControllerTest;
import com.bathanh.apibook.api.WithMockAdmin;
import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.domain.user.User;
import com.bathanh.apibook.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTO;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @MockBean
    private UserService userService;

    @MockBean
    private AuthsProvider authsProvider;

    @BeforeEach
    void init() {
        when(authsProvider.getCurrentAuthentication())
                .thenCallRealMethod();
    }

    @Test
    @WithMockAdmin
    void findAll_OK() throws Exception {
        final var users = buildUsers();

        when(userService.findAll()).thenReturn(users);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(users.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(users.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(users.get(0).isEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(users.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(users.get(0).getRoleId().toString()));

        verify(userService).findAll();
    }

    @Test
    @WithMockAdmin
    void search_OK() throws Exception {
        final var user = buildUser();
        final var expected = buildUsers();

        when(userService.search(anyString())).thenReturn(expected);

        get(BASE_URL + "/search?keyword=" + user.getUsername())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expected.size()))
                .andExpect(jsonPath("$[0].id").value(expected.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(expected.get(0).getUsername()))
                .andExpect(jsonPath("$[0].firstName").value(expected.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(expected.get(0).getLastName()))
                .andExpect(jsonPath("$[0].enabled").value(expected.get(0).isEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(expected.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(expected.get(0).getRoleId().toString()));

        verify(userService).search(user.getUsername());
    }

    @Test
    @WithMockAdmin
    void findById_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(user.getId())).thenReturn(user);

        get(BASE_URL + "/" + user.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).findById(user.getId());
    }

    @Test
    @WithMockAdmin
    void create_OK() throws Exception {
        final var user = buildUser();

        when(userService.create(any(User.class))).thenReturn(user);

        post(BASE_URL, user)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).create(any(User.class));
    }

    @Test
    @WithMockAdmin
    void update_OK() throws Exception {
        final var user = buildUser();
        final var updatedUser = buildUser();
        updatedUser.setId(user.getId());

        when(userService.update(any(UUID.class), any(User.class))).thenReturn(updatedUser);

        put(BASE_URL + "/" + user.getId(), toUserDTO(updatedUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId().toString()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.enabled").value(updatedUser.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(updatedUser.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(updatedUser.getRoleId().toString()));

        verify(userService).update(any(UUID.class), any(User.class));
    }

    @Test
    @WithMockAdmin
    void delete_OK() throws Exception {
        final var id = randomUUID();

        doNothing().when(userService).delete(id);

        delete(BASE_URL + "/" + id)
                .andExpect(status().isOk());

        verify(userService).delete(id);
    }
}
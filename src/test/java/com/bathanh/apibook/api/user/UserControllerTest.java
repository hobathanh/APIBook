package com.bathanh.apibook.api.user;

import com.bathanh.apibook.domain.user.User;
import com.bathanh.apibook.domain.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTO;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUsers;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String BASE_URL = "/api/v1/users";

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void findAllUsers_OK() throws Exception {
        final var users = buildUsers();

        when(userService.findAll()).thenReturn(users);

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
                .andExpect(jsonPath("$[0].password").value(users.get(0).getPassword()))
                .andExpect(jsonPath("$[0].firstname").value(users.get(0).getFirstname()))
                .andExpect(jsonPath("$[0].lastname").value(users.get(0).getLastname()))
                .andExpect(jsonPath("$[0].enabled").value(users.get(0).isEnabled()))
                .andExpect(jsonPath("$[0].avatar").value(users.get(0).getAvatar()))
                .andExpect(jsonPath("$[0].roleId").value(users.get(0).getRoleId().toString()));

        verify(userService).findAll();
    }

    @Test
    void findUserById_OK() throws Exception {
        final var user = buildUser();

        when(userService.findById(user.getId())).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.firstname").value(user.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(user.getLastname()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).findById(user.getId());
    }

    @Test
    void createUser_OK() throws Exception {
        final var user = buildUser();

        when(userService.createUser(any(User.class))).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.firstname").value(user.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(user.getLastname()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).createUser(any(User.class));
    }

    @Test
    void updateUser_OK() throws Exception {
        final var user = buildUser();
        final var updatedUser = buildUser();
        updatedUser.setId(user.getId());

        when(userService.updateUser(any(UUID.class), any(User.class))).thenReturn(updatedUser);

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(toUserDTO(updatedUser))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId().toString()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.password").value(updatedUser.getPassword()))
                .andExpect(jsonPath("$.firstname").value(updatedUser.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(updatedUser.getLastname()))
                .andExpect(jsonPath("$.enabled").value(updatedUser.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(updatedUser.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(updatedUser.getRoleId().toString()));

        verify(userService).updateUser(any(UUID.class), any(User.class));
    }

    @Test
    void deleteUser() throws Exception {
        final var id = randomUUID();

        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + id))
                .andExpect(status().isOk());

        verify(userService).deleteUser(id);
    }
}
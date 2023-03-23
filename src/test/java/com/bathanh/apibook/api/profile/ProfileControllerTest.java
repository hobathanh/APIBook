package com.bathanh.apibook.api.profile;

import com.bathanh.apibook.api.AbstractControllerTest;
import com.bathanh.apibook.api.WithMockAdmin;
import com.bathanh.apibook.api.WithMockContributor;
import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.domain.user.User;
import com.bathanh.apibook.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@ExtendWith(SpringExtension.class)
class ProfileControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/profile";

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
    @WithMockContributor
    void shouldGetProfile_OK() throws Exception {
        final var user = buildUser();

        when(userService.findUserProfile()).thenReturn(user);

        get(BASE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.enabled").value(user.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(user.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(user.getRoleId().toString()));

        verify(userService).findUserProfile();
    }

    @Test
    @WithMockAdmin
    @WithMockContributor
    void shouldUpdateProfile_OK() throws Exception {
        final var updatedUser = buildUser();

        when(userService.updateUserProfile(any(User.class))).thenReturn(updatedUser);

        put(BASE_URL, updatedUser)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId().toString()))
                .andExpect(jsonPath("$.username").value(updatedUser.getUsername()))
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.enabled").value(updatedUser.isEnabled()))
                .andExpect(jsonPath("$.avatar").value(updatedUser.getAvatar()))
                .andExpect(jsonPath("$.roleId").value(updatedUser.getRoleId().toString()));
    }
}
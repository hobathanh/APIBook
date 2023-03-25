package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.error.UsernameNotFoundException;
import com.bathanh.apibook.persistence.role.RoleStore;
import com.bathanh.apibook.persistence.user.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.bathanh.apibook.fakes.RoleFakes.buildRole;
import static com.bathanh.apibook.fakes.UserFakes.buildUserEntity;
import static com.bathanh.apibook.persistence.user.UserEntityMapper.toUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @Mock
    private UserStore userStore;
    @Mock
    private RoleStore roleStore;
    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    void loadUserByUsername_whenUsernameNotFound_OK() {
        final var user = buildUserEntity();
        final var role = buildRole();
        role.setId(user.getRoleId());

        when(userStore.findByUsername(anyString())).thenReturn(Optional.of(toUser(user)));
        when(roleStore.findRoleById(role.getId())).thenReturn(role);

        final UserDetails actual = jwtUserDetailsService.loadUserByUsername(user.getUsername());

        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());

        verify(userStore).findByUsername(user.getUsername());
        verify(roleStore).findRoleById(role.getId());
    }

    @Test
    void verifyLoadUserByUsername_ThrowUsernameNotFoundException() {
        when(userStore.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername("Non existent username"));

        verify(userStore).findByUsername("Non existent username");
    }
}
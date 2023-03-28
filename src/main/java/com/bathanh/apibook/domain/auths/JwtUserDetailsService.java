package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.domain.role.Role;
import com.bathanh.apibook.error.UsernameNotFoundException;
import com.bathanh.apibook.persistence.role.RoleStore;
import com.bathanh.apibook.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserStore userStore;

    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userStore.findByUsername(username)
                .map(this::buildUser)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private User buildUser(final com.bathanh.apibook.domain.user.User user) {
        final Role role = roleStore.findRoleById(user.getRoleId());

        return new JwtUserDetails(user.getId(),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role.getName())));
    }
}

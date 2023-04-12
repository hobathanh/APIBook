package com.bathanh.apibook.domain.auths;

import com.bathanh.apibook.error.UsernameNotFoundException;
import com.bathanh.apibook.persistence.role.RoleStore;
import com.bathanh.apibook.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.bathanh.apibook.domain.auths.UserDetailsMapper.toUserDetails;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserStore userStore;

    private final RoleStore roleStore;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userStore.findByUsername(username)
                .map(user -> toUserDetails(user, roleStore.findRoleById(user.getRoleId()).getName()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.persistence.role.RoleStore;
import com.bathanh.apibook.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.domain.auths.UserDetailsMapper.toUserDetails;
import static com.bathanh.apibook.domain.user.UserError.supplyUserAlreadyExist;
import static com.bathanh.apibook.domain.user.UserError.supplyUserNotFound;
import static com.bathanh.apibook.domain.user.UserValidation.validateCreateUser;
import static com.bathanh.apibook.domain.user.UserValidation.validateUpdateUser;
import static com.bathanh.apibook.error.CommonError.supplyForbiddenError;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;
    private final AuthsProvider authsProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleStore roleStore;
    private final FacebookService facebookService;

    public UserDetails loginWithFacebook(final String facebookToken) {
        final SocialUser socialUser = facebookService.parseToken(facebookToken);

        return userStore.findByUsername(socialUser.getId())
                .map(user -> toUserDetails(user, "CONTRIBUTOR"))
                .orElseGet(() -> {
                    final User user = createNewUserFromSocialUser(socialUser);
                    return toUserDetails(user, "CONTRIBUTOR");
                });
    }

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User findById(final UUID userId) {
        verifyUserProfilePermission(userId);

        return userStore.findById(userId)
                .orElseThrow(supplyUserNotFound(userId));
    }

    public User findByUsername(final String username) {
        return userStore.findByUsername(username).orElseThrow(supplyUserNotFound(username));
    }

    public List<User> search(final String keyword) {
        return userStore.search(keyword);
    }

    public User create(final User user) {
        validateCreateUser(user);
        verifyUsernameAvailable(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userStore.create(user);
    }

    public User update(final UUID id, final User user) {
        final User updatedUser = findById(id);
        validateUpdateUser(user);
        verifyUpdateUserPermission(updatedUser);

        if (!(user.getUsername().equals(updatedUser.getUsername()))) {
            verifyUsernameAvailable(user.getUsername());
            updatedUser.setUsername(user.getUsername());
        }

        if (isNotBlank(user.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEnabled(user.isEnabled());
        updatedUser.setAvatar(user.getAvatar());
        updatedUser.setRoleId(user.getRoleId());

        return userStore.update(updatedUser);
    }

    public void delete(final UUID userId) {
        findById(userId);
        userStore.delete(userId);
    }

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);
        if (userOptional.isPresent()) {
            throw supplyUserAlreadyExist(username).get();
        }
    }

    private void verifyUpdateUserPermission(final User user) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(user.getId())) {
            throw supplyForbiddenError().get();
        }
    }

    private void verifyUserProfilePermission(final UUID userId) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(userId)) {
            throw supplyForbiddenError().get();
        }
    }

    private User createNewUserFromSocialUser(final SocialUser socialUser) {
        final User user = User.builder()
                .username(socialUser.getId())
                .password(randomUUID().toString())
                .firstName(socialUser.getFirstName())
                .lastName(socialUser.getLastName())
                .enabled(true)
                .roleId(roleStore.findByName("CONTRIBUTOR").getId())
                .build();

        return userStore.create(user);
    }
}

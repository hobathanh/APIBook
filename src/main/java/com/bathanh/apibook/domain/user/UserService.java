package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.domain.auths.AuthsProvider;
import com.bathanh.apibook.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.domain.user.UserError.supplyUserAlreadyExist;
import static com.bathanh.apibook.domain.user.UserError.supplyUserNotFound;
import static com.bathanh.apibook.domain.user.UserValidation.validateCreateUser;
import static com.bathanh.apibook.domain.user.UserValidation.validateUpdateUser;
import static com.bathanh.apibook.error.CommonError.supplyForbiddenError;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;
    private final AuthsProvider authsProvider;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User findById(final UUID id) {
        verifyUserProfilePermission(id);

        return userStore.findById(id)
                .orElseThrow(supplyUserNotFound(id));
    }

    public User findUserProfile() {
        return findById(authsProvider.getCurrentUserId());
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
        verifyUpdateUserPermission(id);

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

    public User updateUserProfile(final User userUpdate) {
        return update(authsProvider.getCurrentUserId(), userUpdate);
    }

    public void delete(final UUID id) {
        final User user = findById(id);
        userStore.delete(user);
    }

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);
        if (userOptional.isPresent()) {
            throw supplyUserAlreadyExist(username).get();
        }
    }

    private void verifyUpdateUserPermission(UUID id) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(id)) {
            throw supplyForbiddenError("You do not have permission to update user").get();
        }
    }

    private void verifyUserProfilePermission(UUID id) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(id)) {
            throw supplyForbiddenError("You do not have permission to view user").get();
        }
    }
}

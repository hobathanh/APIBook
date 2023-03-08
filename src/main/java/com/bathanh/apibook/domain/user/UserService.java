package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.UserAlreadyExistException;
import com.bathanh.apibook.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.domain.user.UserError.supplyUserNotFound;
import static com.bathanh.apibook.domain.user.UserValidation.validateCreateUser;
import static com.bathanh.apibook.domain.user.UserValidation.validateUpdateUser;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAll();
    }

    public User findById(final UUID id) {
        return userStore.findById(id)
                .orElseThrow(supplyUserNotFound(id));
    }

    public List<User> search(final String keyword) {
        return userStore.search(keyword);
    }

    public User create(final User user) {
        validateCreateUser(user);
        verifyUsernameAvailable(user.getUsername());

        return userStore.create(user);
    }

    public User update(final UUID id, final User user) {
        final User updatedUser = findById(id);
        validateUpdateUser(user);

        if (!(user.getUsername().equals(updatedUser.getUsername()))) {
            verifyUsernameAvailable(user.getUsername());
            updatedUser.setUsername(user.getUsername());
        }

        updatedUser.setUsername(updatedUser.getUsername());

        if (isBlank(user.getPassword())) {
            updatedUser.setPassword(updatedUser.getPassword());
        }

        updatedUser.setPassword(user.getPassword());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEnabled(user.isEnabled());
        updatedUser.setAvatar(user.getAvatar());

        return userStore.update(updatedUser);
    }

    public void delete(final UUID id) {
        findById(id);
        userStore.delete(id);
    }

    private void verifyUsernameAvailable(final String username) {
        final Optional<User> userOptional = userStore.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("Username already exists");
        }
    }
}

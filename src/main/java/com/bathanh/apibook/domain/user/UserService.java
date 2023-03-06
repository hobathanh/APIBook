package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.UserAlreadyExistException;
import com.bathanh.apibook.persistence.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.domain.user.UserError.supplyUserNotFound;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStore userStore;

    public List<User> findAll() {
        return userStore.findAllUsers();
    }

    public User findById(final UUID id) {
        return userStore.findUserById(id)
                .orElseThrow(supplyUserNotFound(id));
    }


    private void verifyUsernameAvailable(final String username) {
        final Optional<User> userOptional = userStore.findUserByUsername(username);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("Username already exists");
        }
    }

    public User createUser(final User user) {
        verifyUsernameAvailable(user.getUsername());
        return userStore.createUser(user);
    }

    public User updateUser(final UUID id, final User updatedUser) {
        final User user = findById(id);

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setEnabled(updatedUser.isEnabled());
        user.setAvatar(updatedUser.getAvatar());

        return userStore.updateUser(user);
    }

    public void deleteUser(final UUID id) {
        findById(id);
        userStore.deleteUser(id);
    }
}

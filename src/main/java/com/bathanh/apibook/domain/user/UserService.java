package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.UserAvailableException;
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

    public List<User> findAllUsers() {
        return userStore.findAllUsers();
    }

    public User findUserById(final UUID id) {
        return userStore.findUserById(id)
                .orElseThrow(supplyUserNotFound(id));
    }


    private void verifyUserAvailable(final User user) {
        final Optional<User> userOptional = userStore.findUserByUserName(user.getUsername());
        if (userOptional.isPresent()) {
            throw new UserAvailableException("User Available!");
        }
    }

    public User createUser(final User user) {
        verifyUserAvailable(user);
        return userStore.createUser(user);
    }

    public User updateUser(final UUID id, final User updatedUser) {
        final User user = findUserById(id);

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEnabled(updatedUser.isEnabled());
        user.setAvatar(updatedUser.getAvatar());

        return userStore.updateUser(user);
    }

    public void deleteUser(final UUID id) {
        findUserById(id);
        userStore.deleteUser(id);
    }
}

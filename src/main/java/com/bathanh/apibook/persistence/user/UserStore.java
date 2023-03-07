package com.bathanh.apibook.persistence.user;

import com.bathanh.apibook.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bathanh.apibook.persistence.user.UserEntityMapper.*;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return toUsers(toList(userRepository.findAll()));
    }

    public List<User> searchUsers(final String keyWord) {
        return userRepository.findAllByFirstnameContainingOrLastnameContainingOrUsernameContaining(keyWord, keyWord, keyWord)
                .stream().map(UserEntityMapper::toUser).collect(Collectors.toList());
    }

    public Optional<User> findUserById(final UUID uuid) {
        return userRepository.findById(uuid)
                .map(UserEntityMapper::toUser);
    }

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(UserEntityMapper::toUser);
    }

    public User createUser(final User user) {
        return toUser(userRepository.save(toUserEntity(user)));
    }

    public User updateUser(final User updatedUser) {
        return toUser(userRepository.save(toUserEntity(updatedUser)));
    }

    public void deleteUser(final UUID id) {
        userRepository.deleteById(id);
    }
}

package com.bathanh.apibook.persistence.user;

import com.bathanh.apibook.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bathanh.apibook.persistence.user.UserEntityMapper.*;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return toUsers(toList(userRepository.findAll()));
    }

    public List<User> search(final String keyword) {
        return toUsers(userRepository.findAllByFirstNameOrLastNameOrUsername(keyword));
    }

    public Optional<User> findById(final UUID uuid) {
        return userRepository.findById(uuid)
                .map(UserEntityMapper::toUser);
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username)
                .map(UserEntityMapper::toUser);
    }

    public User create(final User user) {
        return toUser(userRepository.save(toUserEntity(user)));
    }

    public User update(final User user) {
        return toUser(userRepository.save(toUserEntity(user)));
    }

    public void delete(final UUID userId) {
        userRepository.deleteById(userId);
    }
}

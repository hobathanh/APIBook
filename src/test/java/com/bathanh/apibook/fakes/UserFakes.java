package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.user.User;
import com.bathanh.apibook.persistence.user.UserEntity;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@UtilityClass
public class UserFakes {

    public static User buildUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username(RandomStringUtils.randomAlphabetic(3, 10))
                .password(RandomStringUtils.randomAlphabetic(3, 10))
                .firstName(RandomStringUtils.randomAlphabetic(3, 10))
                .lastName(RandomStringUtils.randomAlphabetic(3, 10))
                .enabled(Boolean.TRUE)
                .avatar(RandomStringUtils.randomAlphabetic(3, 10))
                .roleId(UUID.randomUUID())
                .build();
    }

    public static List<User> buildUsers() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildUser())
                .toList();
    }

    public static UserEntity buildUserEntity() {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .username(RandomStringUtils.randomAlphabetic(3, 10))
                .password(RandomStringUtils.randomAlphabetic(3, 10))
                .firstName(RandomStringUtils.randomAlphabetic(3, 10))
                .lastName(RandomStringUtils.randomAlphabetic(3, 10))
                .enabled(Boolean.TRUE)
                .avatar(RandomStringUtils.randomAlphabetic(3, 10))
                .roleId(UUID.randomUUID())
                .build();
    }

    public static List<UserEntity> buildUserEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildUserEntity())
                .toList();
    }
}

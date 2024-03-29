package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.bathanh.apibook.domain.user.UserValidation.validateCreateUser;
import static com.bathanh.apibook.domain.user.UserValidation.validateUpdateUser;
import static com.bathanh.apibook.fakes.UserFakes.buildUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserValidationTest {

    @Test
    void validateCreateUser_OK() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(6, 10));

        validateCreateUser(user);
    }

    @Test
    void validateCreateUser_ThrowUsernameEmpty() {
        final var user = buildUser();
        user.setUsername(null);

        assertThrows(BadRequestException.class, () -> validateCreateUser(user));
    }

    @Test
    void validateCreateUser_ThrowPasswordEmpty() {
        final var user = buildUser();
        user.setPassword(null);

        assertThrows(BadRequestException.class, () -> validateCreateUser(user));
    }

    @Test
    void validateCreateUser_ThrowLengthPassword() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(3, 5));

        assertThrows(BadRequestException.class, () -> validateCreateUser(user));
    }

    @Test
    void validateUpdateUser_OK() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(6, 10));

        validateUpdateUser(user);
    }

    @Test
    void validateUpdateUser_ThrowUsernameEmpty() {
        final var user = buildUser();
        user.setUsername(null);

        assertThrows(BadRequestException.class, () -> validateUpdateUser(user));
    }

    @Test
    void validateUpdateUser_ThrowLengthPassword() {
        final var user = buildUser();
        user.setPassword(randomAlphabetic(3, 5));

        assertThrows(BadRequestException.class, () -> validateUpdateUser(user));
    }
}
package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.BadRequestException;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UserValidation {

    public static void validateCreateUser(final User user) {
        validateUserName(user.getUsername());
        validatePasswordEmpty(user.getPassword());
        validateLengthPassword(user.getPassword());
    }

    public static void validateUpdateUser(final User user) {
        validateUserName(user.getUsername());
        validateLengthPassword(user.getPassword());
    }

    private static void validateUserName(final String username) {
        if (username == null || isBlank(username)) {
            throw new BadRequestException("username is required, please check again");
        }
    }

    private static void validatePasswordEmpty(final String password) {
        if (password == null || isBlank(password)) {
            throw new BadRequestException("password is required, please check again");
        }
    }

    private static void validateLengthPassword(final String password) {
        if (isNotBlank(password) && password.length() < 6) {
            throw new BadRequestException("password length must be at least 6 characters");
        }
    }
}

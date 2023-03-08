package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.BadRequestException;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UserValidation {

    public static void validateCreateUser(final User user) {
        validateUserName(user.getUsername());
        validatePasswordEmpty(user.getPassword());
        validatePasswordNotEmpty(user.getPassword());
    }

    public static void validateUpdateUser(final User user) {
        validateUserName(user.getUsername());
        validatePasswordNotEmpty(user.getPassword());
    }

    private static void validateUserName(final String username) {
        if (username == null || isBlank(username)) {
            throw new BadRequestException("Request failed, username is required");
        }
    }

    private static void validatePasswordEmpty(final String password) {
        if (password == null || isBlank(password)) {
            throw new BadRequestException("Request failed, password is required");
        }
    }

    private static void validatePasswordNotEmpty(final String password) {
        if (isNotBlank(password) && password.length() <= 6) {
            throw new BadRequestException("Request failed, password length must be at least 6 characters");
        }
    }
}

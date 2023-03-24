package com.bathanh.apibook.error;

import static java.lang.String.format;

public class UsernameNotFoundException extends NotFoundException {

    public UsernameNotFoundException(final String username, Object... args) {
        super(format("User not found with username: %s", username), args);
    }
}
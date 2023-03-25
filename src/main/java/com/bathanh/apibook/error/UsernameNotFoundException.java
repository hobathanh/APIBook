package com.bathanh.apibook.error;

public class UsernameNotFoundException extends NotFoundException {

    public UsernameNotFoundException(final String username) {
        super("User not found with username: %s", username);
    }
}
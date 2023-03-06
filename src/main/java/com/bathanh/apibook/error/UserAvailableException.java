package com.bathanh.apibook.error;

import org.springframework.http.HttpStatus;

public class UserAvailableException extends DomainException {

    public UserAvailableException(final String message, Object... args) {
        super(HttpStatus.CONFLICT, message, args);
    }
}

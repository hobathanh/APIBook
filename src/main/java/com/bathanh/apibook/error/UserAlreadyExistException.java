package com.bathanh.apibook.error;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends DomainException {

    public UserAlreadyExistException(final String message, Object... args) {
        super(HttpStatus.BAD_REQUEST, message, args);
    }
}

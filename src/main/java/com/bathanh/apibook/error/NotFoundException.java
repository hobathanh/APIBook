package com.bathanh.apibook.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DomainException {

    public NotFoundException(final String message, Object... args) {
        super(HttpStatus.NOT_FOUND, message, args);
    }
}

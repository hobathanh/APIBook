package com.bathanh.apibook.error;

import org.springframework.http.HttpStatus;

public class PropertyInputNotFoundException extends DomainException {

    public PropertyInputNotFoundException(final String message, Object... args) {
        super(HttpStatus.FOUND, message, args);
    }
}


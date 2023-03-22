package com.bathanh.apibook.error;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends DomainException {

    public ForbiddenException(String message, Object... args) {
        super(HttpStatus.FORBIDDEN, message, args);
    }
}

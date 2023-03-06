package com.bathanh.apibook.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {

    private final HttpStatus httpStatus;

    public DomainException(final HttpStatus httpStatus, final String message, Object... args) {
        super(String.format(message, args));
        this.httpStatus = httpStatus;
    }
}

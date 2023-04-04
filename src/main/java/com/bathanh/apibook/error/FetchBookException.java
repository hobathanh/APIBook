package com.bathanh.apibook.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FetchBookException extends ResponseStatusException {

    public FetchBookException(String message) {
        super(HttpStatus.FOUND, message);
    }
}
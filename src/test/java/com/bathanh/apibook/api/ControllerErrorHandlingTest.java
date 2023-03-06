package com.bathanh.apibook.api;

import com.bathanh.apibook.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ControllerErrorHandlingTest {

    private final ControllerErrorHandling controllerErrorHandling = new ControllerErrorHandling();

    @Test
    void shouldHandleDomainException_OK() {
        final var error = new NotFoundException("Message");
        final var response = controllerErrorHandling.handleDomainException(error);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());

        assertEquals("Message", response.getBody().getMessage());
        assertNotNull(response.getBody().getOccurAt());
    }
}
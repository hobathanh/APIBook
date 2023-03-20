package com.bathanh.apibook.error;

import java.util.function.Supplier;

public class CommonError {

    public static Supplier<AccessDeniedException> supplyAccessDeniedError(final String message) {
        return () -> new AccessDeniedException(message);
    }
}

package com.bathanh.apibook.error;

import java.util.function.Supplier;

public class CommonError {

    public static Supplier<ForbiddenException> supplyForbiddenError(final String message) {
        return () -> new ForbiddenException(message);
    }
}

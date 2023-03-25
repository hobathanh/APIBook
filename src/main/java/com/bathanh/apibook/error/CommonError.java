package com.bathanh.apibook.error;

import java.util.function.Supplier;

public class CommonError {

    public static Supplier<ForbiddenException> supplyForbiddenError() {
        return () -> new ForbiddenException("You are not permission to perform this action");
    }
}

package com.bathanh.apibook.domain.role;

import com.bathanh.apibook.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class RoleError {

    public static <T> Supplier<NotFoundException> supplyRoleNotFound(final T input) {
        return () -> new NotFoundException("Role %s could not be found", input);
    }
}

package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class UserError {

    public static <T> Supplier<NotFoundException> supplyUserNotFound(final T input) {
        return () -> new NotFoundException("Username %s could not be found", input);
    }

    public static Supplier<BadRequestException> supplyUserAlreadyExist(final String username) {
        return () -> new BadRequestException("User with username %s already exist", username);
    }
}

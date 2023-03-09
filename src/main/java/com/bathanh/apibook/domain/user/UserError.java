package com.bathanh.apibook.domain.user;

import com.bathanh.apibook.error.BadRequestException;
import com.bathanh.apibook.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class UserError {

    public static Supplier<NotFoundException> supplyUserNotFound(final UUID id) {
        return () -> new NotFoundException("User with id %s could not be found", id);
    }

    public static Supplier<BadRequestException> supplyUserAlreadyExist(final String username) {
        return () -> new BadRequestException("User with username %s already exist", username);
    }
}

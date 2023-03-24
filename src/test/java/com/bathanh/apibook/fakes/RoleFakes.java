package com.bathanh.apibook.fakes;

import com.bathanh.apibook.domain.role.Role;
import com.bathanh.apibook.persistence.role.RoleEntity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class RoleFakes {

    public static RoleEntity buildRoleEntity() {
        return RoleEntity.builder()
                .id(UUID.randomUUID())
                .name(randomAlphabetic(3, 10))
                .build();
    }

    public static Role buildRole() {
        return Role.builder()
                .id(UUID.randomUUID())
                .name(randomAlphabetic(3, 10))
                .build();
    }
}
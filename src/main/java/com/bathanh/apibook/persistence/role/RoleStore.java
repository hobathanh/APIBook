package com.bathanh.apibook.persistence.role;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RoleStore {

    public String getRoleById(final UUID uuid) {
        if (uuid.equals(UUID.fromString("dddddddd-3e86-4ad6-bcd8-f78b0fd263f8"))) {
            return "ROLE_USER";
        }

        if (uuid.equals(UUID.fromString("eeeeeeee-be2e-4f19-b48e-2c52f189b9dd"))) {
            return "ROLE_ADMIN";
        }

        return null;
    }
}

package com.bathanh.apibook.domain.role;

import com.bathanh.apibook.persistence.role.RoleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public static Role toRole(final RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();
    }
}

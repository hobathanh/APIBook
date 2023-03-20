package com.bathanh.apibook.persistence.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public String findRoleNameById(final UUID id) {
        return roleRepository.findById(id).orElse(null).getName();
    }
}

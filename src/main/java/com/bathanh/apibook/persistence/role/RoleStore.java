package com.bathanh.apibook.persistence.role;

import com.bathanh.apibook.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.bathanh.apibook.domain.role.RoleError.supplyRoleNotFound;
import static com.bathanh.apibook.domain.role.RoleMapper.toRole;

@Repository
@RequiredArgsConstructor
public class RoleStore {

    private final RoleRepository roleRepository;

    public Role findRoleById(final UUID id) {
        return toRole(roleRepository.findById(id).orElseThrow(supplyRoleNotFound(id)));
    }

    public Role findByName(final String name) {
        return toRole(roleRepository.findByName(name).orElseThrow(supplyRoleNotFound(name)));
    }
}

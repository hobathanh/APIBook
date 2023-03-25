package com.bathanh.apibook.persistence.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {

}

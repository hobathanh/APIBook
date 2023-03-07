package com.bathanh.apibook.persistence.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(final String username);

    List<UserEntity> findAllByFirstnameContainingOrLastnameContainingOrUsernameContaining(final String firstname, final String lastname, final String username);
}

package com.bathanh.apibook.persistence.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bathanh.apibook.fakes.RoleFakes.buildRoleEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleStoreTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleStore roleStore;

    @Test
    void shouldFindRoleById_OK() {
        final var role = buildRoleEntity();
        final var expected = Optional.of(role);

        when(roleRepository.findById(role.getId())).thenReturn(expected);

        final var actual = roleStore.findRoleById(role.getId());

        assertEquals(expected.get().getId(), actual.getId());
        assertEquals(expected.get().getName(), actual.getName());

        verify(roleRepository).findById(role.getId());
    }
}

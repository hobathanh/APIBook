package com.bathanh.apibook.api.user;

import com.bathanh.apibook.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTO;
import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTOs;
import static com.bathanh.apibook.domain.user.UserMapper.toUser;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Find all available users")
    @GetMapping
    public List<UserResponseDTO> findAll() {
        return toUserDTOs(userService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Find a specific user by id")
    @GetMapping("{id}")
    public UserResponseDTO findById(final @PathVariable UUID id) {
        return toUserDTO(userService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Search users by keyword")
    @GetMapping("search")
    public List<UserResponseDTO> search(final @RequestParam String keyword) {
        return toUserDTOs(userService.search(keyword));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Create a specific user")
    @PostMapping
    public UserResponseDTO create(final @RequestBody UserRequestDTO userRequestDTO) {
        return toUserDTO(userService.create(toUser(userRequestDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Update a specific user")
    @PutMapping("{id}")
    public UserResponseDTO update(final @PathVariable UUID id, final @RequestBody UserRequestDTO userRequestDTO) {
        return toUserDTO(userService.update(id, toUser(userRequestDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Delete a specific user")
    @DeleteMapping("{id}")
    public void delete(final @PathVariable UUID id) {
        userService.delete(id);
    }
}

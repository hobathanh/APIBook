package com.bathanh.apibook.api.user;

import com.bathanh.apibook.domain.user.User;
import com.bathanh.apibook.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.bathanh.apibook.api.user.UserDTOMapper.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Find all available users")
    @GetMapping
    public List<UserDTO> findAllUsers() {
        return toUserDTOs(userService.findAll());
    }

    @Operation(summary = "Find a specific user by id")
    @GetMapping("{id}")
    public UserDTO findUserById(final @PathVariable UUID id) {
        return toUserDTO(userService.findById(id));
    }

    @Operation(summary = "Create a specific user")
    @PostMapping
    public UserDTO createUser(final @RequestBody User user) {
        return toUserDTO(userService.createUser(user));
    }

    @Operation(summary = "Update a specific user")
    @PutMapping("{id}")
    public UserDTO updateUser(final @PathVariable UUID id, final @RequestBody UserDTO userDTO) {
        return toUserDTO(userService.updateUser(id, toUser(userDTO)));
    }

    @Operation(summary = "Delete a specific user")
    @DeleteMapping("{id}")
    public void deleteUser(final @PathVariable UUID id) {
        userService.deleteUser(id);
    }
}

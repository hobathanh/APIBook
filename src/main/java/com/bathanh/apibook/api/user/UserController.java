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
        return toUserDTOs(userService.findAllUsers());
    }

    @Operation(summary = "Find a specific user by id")
    @GetMapping("{id}")
    public UserDTO findUserById(@PathVariable UUID id) {
        return toUserDTO(userService.findUserById(id));
    }

    @Operation(summary = "Create a specific user")
    @PostMapping
    public UserDTO CreateUser(@RequestBody User user) {
        return toUserDTO(userService.createUser(user));
    }

    @Operation(summary = "Update a specific user")
    @PutMapping("{id}")
    public UserDTO UpdateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return toUserDTO(userService.updateUser(id, toUser(userDTO)));
    }

    @Operation(summary = "Delete a specific user")
    @DeleteMapping("{id}")
    public void DeleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}

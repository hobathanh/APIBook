package com.bathanh.apibook.api.profile;

import com.bathanh.apibook.api.user.UserRequestDTO;
import com.bathanh.apibook.api.user.UserResponseDTO;
import com.bathanh.apibook.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTO;
import static com.bathanh.apibook.domain.user.UserMapper.toUser;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('CONTRIBUTOR','ADMIN')")
    @Operation(summary = "Get the current user's information")
    @GetMapping("{id}")
    public UserResponseDTO findById(final @PathVariable UUID id) {
        return toUserDTO(userService.findById(id));
    }

    @PreAuthorize("hasAnyRole('CONTRIBUTOR','ADMIN')")
    @Operation(summary = "Update current user's profile")
    @PutMapping("{id}")
    public UserResponseDTO update(final @PathVariable UUID id, final @RequestBody UserRequestDTO userRequestDTO) {
        return toUserDTO(userService.update(id, toUser(userRequestDTO)));
    }
}
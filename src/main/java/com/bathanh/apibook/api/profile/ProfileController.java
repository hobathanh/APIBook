package com.bathanh.apibook.api.profile;

import com.bathanh.apibook.api.user.UserRequestDTO;
import com.bathanh.apibook.api.user.UserResponseDTO;
import com.bathanh.apibook.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.bathanh.apibook.api.user.UserDTOMapper.toUserDTO;
import static com.bathanh.apibook.domain.user.UserMapper.toUser;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CONTRIBUTOR','ADMIN')")
public class ProfileController {

    private final UserService userService;

    @Operation(summary = "Get the current user's information")
    @GetMapping
    public UserResponseDTO getProfile() {
        return toUserDTO(userService.findUserProfile());
    }

    @Operation(summary = "Update current user's profile")
    @PutMapping
    public UserResponseDTO updateProfile(final @RequestBody UserRequestDTO userRequestDTO) {
        return toUserDTO(userService.updateUserProfile(toUser(userRequestDTO)));
    }
}
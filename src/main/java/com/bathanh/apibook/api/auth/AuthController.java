package com.bathanh.apibook.api.auth;

import com.bathanh.apibook.domain.auths.JwtTokenService;
import com.bathanh.apibook.domain.auths.JwtUserDetails;
import com.bathanh.apibook.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bathanh.apibook.api.auth.LoginDTOMapper.toAuthentication;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @PostMapping
    public JwtTokenResponseDTO login(final @RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return generateJwtToken((JwtUserDetails) authentication.getPrincipal());
    }

    @PostMapping("/facebook")
    public JwtTokenResponseDTO loginWithFacebook(final @RequestBody TokenRequestDTO tokenRequestDTO) {
        final UserDetails userDetails = userService.loginWithFacebook(tokenRequestDTO.getAccessToken());

        return generateJwtToken((JwtUserDetails) userDetails);
    }

    private JwtTokenResponseDTO generateJwtToken(final JwtUserDetails userDetails) {
        return JwtTokenResponseDTO.builder()
                .token(jwtTokenService.generateToken(userDetails))
                .build();
    }
}

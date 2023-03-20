package com.bathanh.apibook.api.auth;

import com.bathanh.apibook.domain.auths.JwtTokenService;
import com.bathanh.apibook.domain.auths.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bathanh.apibook.api.auth.LoginDTOMapper.toAuthentication;

@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public JwtTokenResponseDTO login(@RequestBody LoginDTO loginDTO) {
        final Authentication authentication = authenticationManager.authenticate(toAuthentication(loginDTO));

        return JwtTokenResponseDTO.builder()
                .token(jwtTokenUtil.generateToken((JwtUserDetails) authentication.getPrincipal()))
                .build();
    }
}

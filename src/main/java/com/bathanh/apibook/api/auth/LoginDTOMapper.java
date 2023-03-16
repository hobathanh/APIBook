package com.bathanh.apibook.api.auth;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@UtilityClass
public class LoginDTOMapper {

    public static Authentication toAuthentication(final LoginDTO loginDTO) {
        return new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        );
    }
}

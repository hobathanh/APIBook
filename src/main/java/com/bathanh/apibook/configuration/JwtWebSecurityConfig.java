package com.bathanh.apibook.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class JwtWebSecurityConfig {

    private static final String[] SWAGGER_RESOURCES = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    private static final String[] LOGIN_RESOURCE = {
            "/api/v1/auths/**"
    };

    private final JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter;

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers(SWAGGER_RESOURCES)
                .antMatchers(LOGIN_RESOURCE);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity httpSecurity,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailService) throws Exception {
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .anonymous()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/books/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtTokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
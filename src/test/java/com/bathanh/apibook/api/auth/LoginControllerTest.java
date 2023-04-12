package com.bathanh.apibook.api.auth;

import com.bathanh.apibook.api.AbstractControllerTest;
import com.bathanh.apibook.api.WithMockAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
public class LoginControllerTest extends AbstractControllerTest {

    @Test
    @WithMockAdmin
    public void shouldLoginPage_OK() throws Exception {
        get("/login-social")
                .andExpect(status().isOk())
                .andExpect(view().name("index.html"));
    }
}
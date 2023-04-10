package com.bathanh.apibook.api.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/facebook")
    public String loginPage() {
        return "index.html";
    }
}


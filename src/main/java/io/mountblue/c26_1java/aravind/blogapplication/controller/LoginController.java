package io.mountblue.c26_1java.aravind.blogapplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog-application")
public class LoginController {
    @GetMapping("/login")
    public String login(HttpSession session) {
        return session.getAttribute("user") == null ? "login-form" : "redirect:/blog-application/";
    }
}

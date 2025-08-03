package com.variomex.variomex.controller;



import com.variomex.variomex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String role = body.get("role");

        return userService.registerUser(email, password, role);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return userService.login(email, password);
    }
}

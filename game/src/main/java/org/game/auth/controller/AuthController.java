package org.game.auth.controller;

import org.game.admin.input.UserInput;
import org.game.admin.model.User;
import org.game.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping("/login")
    public String login(@RequestParam("query") String query, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(query, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userService.findByUsernameOrEmail(query); // Devolver la vista de login (login.html en templates)
    }

    @PostMapping("/register")
    public String register(UserInput userInput) {
        return "register"; // Devolver la vista de registro (register.html en templates)
    }
}

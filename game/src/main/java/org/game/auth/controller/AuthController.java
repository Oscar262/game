package org.game.auth.controller;

import org.game.admin.input.UserInput;
import org.game.admin.model.User;
import org.game.admin.service.UserService;
import org.game.auth.jwt.JwtTokenUtil;
import org.game.auth.jwt.UserDetailsImpl;
import org.game.auth.output.JwtOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping("/login")
    public JwtOutput login(@RequestParam("query") String query, @RequestParam String password) {
       Optional<User> optionalUser = userService.findByUsernameOrEmail(query);
       if (optionalUser.isEmpty())
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exist");
       else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(optionalUser.get().getUsername(), password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtTokenUtil.generateToken(authentication);
            JwtOutput jwtOutput = new JwtOutput(jwt, userDetails.getEmail(), userDetails.getName(), userDetails.getLastname());
            userService.updateLastAccessTime(userDetails.getEmail());

            return jwtOutput;
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody UserInput userInput) {
        try {
            return userService.registerNewUser(userInput);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

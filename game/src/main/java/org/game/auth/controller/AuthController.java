package org.game.auth.controller;

import com.sun.xml.bind.v2.runtime.output.Encoded;
import org.game.admin.input.UserInput;
import org.game.admin.model.User;
import org.game.admin.service.UserService;
import org.game.auth.jwt.JwtTokenUtil;
import org.game.auth.jwt.UserDetailsImpl;
import org.game.auth.output.JwtOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder encoder;


    @PostMapping("/signin")
    public JwtOutput login(@RequestBody UserInput userInput) {
        Optional<User> optionalUser = userService.findByUsernameOrEmail(userInput.getQ());
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not exist");
        else {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(optionalUser.get().getUsername(), userInput.getPassword())
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                String jwt = jwtTokenUtil.generateToken(authentication);
                JwtOutput jwtOutput = new JwtOutput(jwt, userDetails.getEmail(), userDetails.getName(), userDetails.getLastname());
                userService.updateLastAccessTime(userDetails.getEmail());

                return jwtOutput;
            } catch (BadCredentialsException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            } catch (DisabledException e) {
                throw new ResponseStatusException(HttpStatus.LOCKED, e.getMessage());
            }
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody UserInput userInput) {
        try {
            if (userService.findByUsername(userInput.getUsername()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exist");
            }
            if (userService.findByEmail(userInput.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
            }
            User user = new User(userInput.getName(), userInput.getLastName(), userInput.getUsername(), userInput.getEmail(),
                    encoder.encode(userInput.getPassword()), UUID.randomUUID(), 0L, true, java.time.LocalDateTime.now());

            return userService.registerNewUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

package org.game.auth.service;

import org.game.admin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<org.game.admin.model.User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Username %s not found", username));
        else {
            org.game.admin.model.User user = optionalUser.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(!user.isEnabled())
                    .build();
        }
    }
}

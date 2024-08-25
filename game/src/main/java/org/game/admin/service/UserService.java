package org.game.admin.service;

import org.game.admin.input.UserInput;
import org.game.admin.model.User;
import org.game.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsernameOrEmail(String query) {
        return userRepository.findByUsernameOrEmail(query);
    }

    public User registerNewUser(UserInput userInput) throws Exception {
        if (userRepository.findByUsername(userInput.getUsername()).isPresent()) {
            throw new Exception("Username already exist");
        }
        if (userRepository.findByEmail(userInput.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }

        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setEmail(userInput.getEmail());
        //user.setEnabled(true);
        user.setPassword(userInput.getPassword());

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

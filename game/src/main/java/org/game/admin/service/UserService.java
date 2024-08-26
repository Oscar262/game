package org.game.admin.service;

import org.game.admin.input.UserInput;
import org.game.admin.input.UserPagination;
import org.game.admin.input.UserSearch;
import org.game.admin.model.User;
import org.game.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Optional<User> findByUsernameOrEmail(String query) {
        UserPagination userPagination = new UserPagination();
        userPagination.setQ(query);
        userPagination.setLimit(1);
        UserSearch userSearch = new UserSearch();
        return userRepository.findByQuery(userPagination, userSearch).stream().findFirst();
    }

    public User registerNewUser(UserInput userInput) throws Exception {
        if (userRepository.findByUsername(userInput.getUsername()).isPresent()) {
            throw new Exception("Username already exist");
        }
        if (userRepository.findByEmail(userInput.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }
            User user = new User(userInput.getName(), userInput.getLastname(), userInput.getUsername(), userInput.getEmail(),
                    encoder.encode(userInput.getPassword()), UUID.randomUUID(), 0L, true, java.time.LocalDateTime.now());

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateLastAccessTime(String email) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent())
            return updateLastAccessTime(user.get());
        else throw new UsernameNotFoundException(String.format("User with email %s not fount", email));
    }

    private User updateLastAccessTime(User user) {
        user.setLastAccessAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}

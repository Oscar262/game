package org.game.admin.service;

import org.game.admin.input.UserInput;
import org.game.admin.input.UserPagination;
import org.game.admin.input.UserSearch;
import org.game.admin.model.User;
import org.game.admin.repository.UserRepository;
import org.game.auth.jwt.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        //TODO: falla revisar
        Optional<org.game.admin.model.User> optionalUser = findByUsername(username);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Username %s not found", username));
        else {
            return new UserDetailsImpl(optionalUser.get());
        }
    }

    public Optional<User> findByUsernameOrEmail(String query) {
        UserPagination userPagination = new UserPagination();
        userPagination.setQ(query);
        userPagination.setLimit(1);
        UserSearch userSearch = new UserSearch();
        return userRepository.findForLogin(userPagination, userSearch).stream().findFirst();
    }

    public User registerNewUser(User user) throws Exception {
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

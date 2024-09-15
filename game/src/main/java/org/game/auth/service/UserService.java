package org.game.auth.service;

import org.game.auth.input.UserPagination;
import org.game.auth.input.UserSearch;
import org.game.auth.model.User;
import org.game.auth.output.JwtOutput;
import org.game.auth.repository.UserRepository;
import org.game.auth.model.UserDetailsImpl;
import org.game.utils.AuthenticationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacadeImpl authenticationFacade;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<org.game.auth.model.User> optionalUser = findByUsername(username);
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

    public User updateLastAccessTime(String email, String accessToke) {
        Optional<User> optionalUser = findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setToken(accessToke);
            return updateLastAccessTime(user);
        }
        else throw new UsernameNotFoundException(String.format("User with email %s not fount", email));
    }

    public User updateLastAccessTime(User user) {
        user.setLastAccessAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Page<User> getAll(Pageable userPagination, UserSearch userSearch) {
        return userRepository.findAll(userSearch.build(), userPagination);
    }

    public User getUser() {
        Object authObject = authenticationFacade.getAuthentication();
        if (authObject != null){{
            Object userObject = authenticationFacade.getAuthentication().getPrincipal();
            if (userObject != null && userObject instanceof UserDetails){
                UserDetails userDetails = (UserDetails) userObject;
                if (userDetails.getUsername() != "anonymous"){
                    UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userObject;
                    Optional<User> optionalUser = finById(userDetailsImpl.getUserId());
                    if (optionalUser.isPresent())
                        return optionalUser.get();
                }
            }
        }}
        return null;
    }

    public Optional<User> finById(Long userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}

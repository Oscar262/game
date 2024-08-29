package org.game.utils;

import org.game.auth.model.User;
import org.game.auth.service.UserService;
import org.game.auth.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest httpServletRequest = new CachedBodyHttpServletRequest(request);
        try {
            String jwt = parseJwt(httpServletRequest);
            if (jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {
                String email = jwtTokenUtil.getUserNameForJwtToken(jwt);
                Optional<User> optionalUser = userService.findByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    UserDetails userDetails = new UserDetailsImpl(user);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, AuthorityUtils.createAuthorityList("USER")
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    userService.updateLastAccessTime(user);
                } else
                    throw new Exception("User not found. Can not set user authentication");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(httpServletRequest, response);
    }

    private String parseJwt(CachedBodyHttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer"))
            return header.substring(7, header.length());
        return null;
    }
}

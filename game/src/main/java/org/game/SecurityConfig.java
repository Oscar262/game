package org.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactivar CSRF si no es necesario (dependiendo del contexto)
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll() // Permitir acceso a /login y /register
                .anyRequest().authenticated() // Cualquier otra solicitud necesita autenticación
                .and()
                .sessionManagement()
                .maximumSessions(1) // Limitar a 1 sesión por usuario
                .maxSessionsPreventsLogin(false) // Permitir que una nueva sesión invalide la anterior
                .and().and()
                .formLogin()
                .loginPage("/login") // Página de login personalizada
                .defaultSuccessUrl("/home", true) // Redirigir a /home después del login
                .and()
                .logout()
                .logoutUrl("/logout") // Endpoint para logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"); // Invalida la sesión y elimina la cookie JSESSIONID

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

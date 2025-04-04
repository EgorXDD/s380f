package com.example.s380f_gp_xd.config;

import com.example.s380f_gp_xd.entity.User;
import com.example.s380f_gp_xd.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new UserDetailsImpl(user);
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/index-chin.html", "/register", "/login", "/process-login", "/h2-console/**").permitAll() // Allow access
                        .anyRequest().authenticated() // Authenticate all other requests
                )
                .formLogin(login -> login
                        .loginPage("/login") // Custom login page
                        .loginProcessingUrl("/process-login") // Handles login POST requests
                        .defaultSuccessUrl("/welcome", true) // Redirect after successful login
                        .failureUrl("/login?error=true") // Redirect to login page with error on login failure
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Custom logout URL
                        .logoutSuccessUrl("/") // Redirect after successful logout
                );

        // Ensure H2 Console access and frame support
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/index.html", "/index-chin.html")); // Disable CSRF for static pages
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // Allow frames from the same origin

        return http.build();
    }


}

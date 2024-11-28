//class created to break circular dependency
//holds user data

package com.example.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsServiceConfig {
    
    @Autowired
    private PasswordEncoder passwordEncoder; // Autowire the existing PasswordEncoder bean
    
    @Bean (name = "customUserDetailsService") // Renamed the bean to avoid conflict with the SecurityConfig class
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.builder()
                .username( "admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build()
        );
    }
}
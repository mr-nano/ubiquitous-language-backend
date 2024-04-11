package com.example.demo.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetails {

    @Bean
    public UserDetailsService userDetailsService() {
        org.springframework.security.core.userdetails.UserDetails userOne = User.withUsername("user").password("{noop}user").roles("USER").build();
        org.springframework.security.core.userdetails.UserDetails userTwo = User.withUsername("Dhruv").password("{noop}user").roles("USER").build();
        org.springframework.security.core.userdetails.UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN","USER").build();

        return new InMemoryUserDetailsManager(userOne, userTwo, admin);
    }

}

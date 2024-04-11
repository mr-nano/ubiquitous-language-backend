package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/**").hasRole("USER");
        }).csrf().disable();

        return http.httpBasic().and().build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userOne = User.withUsername("user").password("{noop}user").roles("USER").build();
        UserDetails userTwo = User.withUsername("Dhruv").password("{noop}user").roles("USER").build();
        UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN","USER").build();

        return new InMemoryUserDetailsManager(userOne, userTwo, admin);
    }
}

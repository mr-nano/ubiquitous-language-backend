package com.example.demo.user.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("the user is " + userDetails.getUsername());
        System.out.println("the user's password is " + userDetails.getPassword());
        System.out.println("the user authorities are " + userDetails.getAuthorities().stream().toList());

        return userDetails.getUsername();
    }

}

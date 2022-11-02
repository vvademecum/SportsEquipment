package com.example.Acrobatum.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthService {

    public static String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toString().replaceAll("[\\[\\]]", "");
        return role;
    }

}

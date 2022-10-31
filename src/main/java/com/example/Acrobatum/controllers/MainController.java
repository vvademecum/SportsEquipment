package com.example.Acrobatum.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {


    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/success")
    public void loginRedirectPage(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities().toString().replaceAll("[\\[\\]]", "");
        switch (role) {
            case "ROLE_USER":
                response.sendRedirect(request.getContextPath() + "/");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/logIn");
        }
    }

}

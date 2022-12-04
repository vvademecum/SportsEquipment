package com.example.Acrobatum.controllers;

import com.example.Acrobatum.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String mainPage(Model model) {


        model.addAttribute("products", productRepository.findAll());



        return "mainPage";
    }

    @GetMapping("/success")
    public void loginRedirectPage(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities().toString().replaceAll("[\\[\\]]", "");
        switch (role) {
            case "ROLE_USER":
                response.sendRedirect(request.getContextPath() + "/");
                break;
            case "ROLE_ADMIN":
                response.sendRedirect(request.getContextPath() + "/");
                break;
            case "ROLE_CASHIER":
                response.sendRedirect(request.getContextPath() + "/");
                break;
            case "ROLE_HR":
                response.sendRedirect(request.getContextPath() + "/");
                break;
            case "ROLE_STOREKEEPER":
                response.sendRedirect(request.getContextPath() + "/");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/logIn");
        }
    }

}

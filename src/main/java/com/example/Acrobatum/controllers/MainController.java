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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.Acrobatum.controllers.CartController.carts;

@Controller
public class MainController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String mainPage(Model model, HttpSession session) {
        model.addAttribute("products", productRepository.findAll());

        if (session.getAttribute("cart") == null)
            model.addAttribute("carts", null);
        else
            model.addAttribute("carts", carts);

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
                response.sendRedirect(request.getContextPath() + "/backup");
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

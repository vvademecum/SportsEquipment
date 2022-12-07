package com.example.Acrobatum.config;

import com.example.Acrobatum.models.Product;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;

@ControllerAdvice
public class Util {
    @ModelAttribute
    public void sharedData(Model model, HttpSession session, Principal principal) {
        boolean isCartActive = false;

        if (session.getAttribute("cart") != null) {
            HashMap<Long, Integer> cart = (HashMap<Long, Integer>) session.getAttribute("cart");

            isCartActive = true;
        }
        model.addAttribute("isCartActive", isCartActive);

    }
}

package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Product;
import com.example.Acrobatum.models.Product_Cheque;
import com.example.Acrobatum.models.Сheque;
import com.example.Acrobatum.repositories.ClientRepository;
import com.example.Acrobatum.repositories.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public CartController(ProductRepository productRepository, ClientRepository clientRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public Client getAuthClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.findByLogin(authentication.getName());

        return client;
    }

    public static Map<Long, Integer> carts = new HashMap<>();

    @RequestMapping(value = "/addPos", method = RequestMethod.POST)
    public String addProduct(@RequestParam long id,
                             @RequestParam int quantity,
                             HttpSession session,
                             Model model) {
        Product product = productRepository.findById(id).get();

        if (session.getAttribute("cart") == null) {
            HashMap<Long, Integer> cart = new HashMap<>();
            cart.put(product.getId(), quantity);
            session.setAttribute("cart", cart);
            carts = cart;
        } else {
            HashMap<Long, Integer> cart = (HashMap<Long, Integer>) session.getAttribute("cart");
            if (cart.containsKey(product.getId())) {
                int qty = cart.get(product.getId());
                cart.put(product.getId(), cart.get(product.getId()) + quantity);
            } else {
                cart.put(product.getId(), quantity);
                session.setAttribute("cart", cart);
            }
            carts = cart;
        }


        model.addAttribute("product", product);

        return "redirect:/";
    }


    @GetMapping()
    public String cartMainPage(
                               Сheque basket,
                               HttpSession session,
                               Model model) {

        if (session.getAttribute("cart") != null) {
            Map<Product, Integer> cartsProd = new HashMap<>();
            for (Map.Entry<Long, Integer> entry : carts.entrySet()) {
                cartsProd.put(productRepository.findById(entry.getKey()).get(), entry.getValue());
            }
            model.addAttribute("notEmpty", "y");
            model.addAttribute("carts", cartsProd.entrySet());
            System.out.println("not empty");

        } else {
            System.out.println("empty");
            model.addAttribute("notEmpty", "n");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return "cart/main";
    }

    @GetMapping("/clear")
    public String clearCart(
                            HttpSession session,
                            Model model) {
        carts.clear();
        session.removeAttribute("cart");

        return "redirect:/cart";
    }

    @GetMapping("/decProd")
    public String decProd(
                            @RequestParam Long position_id,
                            HttpSession session,
                            Model model) {

        //System.out.println(position_id);
        carts.put(position_id, carts.get(position_id) - 1);

        return "redirect:/cart";
    }

}

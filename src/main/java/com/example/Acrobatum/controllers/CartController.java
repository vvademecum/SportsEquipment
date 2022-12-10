package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.*;
import com.example.Acrobatum.repositories.ChequeRepository;
import com.example.Acrobatum.repositories.ClientRepository;
import com.example.Acrobatum.repositories.ProductRepository;
import com.example.Acrobatum.repositories.Product_ChequeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final Product_ChequeRepository product_chequeRepository;
    private final ChequeRepository chequeRepository;

    public CartController(ProductRepository productRepository, ClientRepository clientRepository, Product_ChequeRepository product_chequeRepository, ChequeRepository chequeRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.product_chequeRepository = product_chequeRepository;
        this.chequeRepository = chequeRepository;
    }

    public Client getAuthClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.findByLogin(authentication.getName());

        return client;
    }

    public static Map<Long, Integer> carts = new HashMap<>();
    public static int result = 0;

    @RequestMapping(value = "/addPos", method = RequestMethod.POST)
    public String addProduct(@RequestParam long id,
                             @RequestParam int quantity,
                             @RequestParam boolean isMainPage,
                             HttpSession session,
                             Model model) {
        Product product = productRepository.findById(id).get();
        session.setAttribute("notEnough", false);

        if (session.getAttribute("cart") == null) {
            HashMap<Long, Integer> cart = new HashMap<>();
            cart.put(product.getId(), quantity);
            session.setAttribute("cart", cart);
            carts = cart;
        } else {
            HashMap<Long, Integer> cart = (HashMap<Long, Integer>) session.getAttribute("cart");
            if (cart.containsKey(product.getId()) && isMainPage)
                cart.put(product.getId(), cart.get(product.getId()) + quantity);
            else
                cart.put(product.getId(), quantity);
            session.setAttribute("cart", cart);
            carts = cart;
        }

        return "redirect:/";
    }


    @GetMapping()
    public String cartMainPage(HttpSession session,
                               Model model) {

        if (session.getAttribute("cart") != null) {
            Map<Product, Integer> cartsProd = new HashMap<>();
            result = 0;
            for (Map.Entry<Long, Integer> entry : carts.entrySet()) {
                cartsProd.put(productRepository.findById(entry.getKey()).get(), entry.getValue());
                result += productRepository.findById(entry.getKey()).get().getCost() * entry.getValue();
            }
            model.addAttribute("notEmpty", "y");
            model.addAttribute("carts", cartsProd.entrySet());
            model.addAttribute("result", result);
        } else
            model.addAttribute("notEmpty", "n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return "cart/main";
    }

    @GetMapping("/clear")
    public String clearCart(HttpSession session) {

        carts.clear();
        result = 0;
        session.removeAttribute("cart");

        return "redirect:/cart";
    }

    @GetMapping("/decProd")
    public String decProd(@RequestParam Long id,
                          @RequestParam Boolean cartPage,
                          HttpSession session,
                          Model model) {
        try {
            if (carts.get(id) <= 1) {
                carts.remove(id);
                if (carts.isEmpty()) {
                    session.removeAttribute("cart");
                    result = 0;
                }
            } else
                carts.put(id, carts.get(id) - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (cartPage)
            return "redirect:/cart";
        else
            return "redirect:/";
    }

    @GetMapping("/incProd")
    public String incProd(@RequestParam Long id,
                          @RequestParam Boolean cartPage,
                          HttpSession session) {

        carts.put(id, carts.get(id) + 1);

        if (cartPage)
            return "redirect:/cart";
        else
            return "redirect:/";
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String addOrder(HttpSession session,
                           Model model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Сheque cheque = new Сheque();
        cheque.setDateOfPurchase(dateFormat.format(new Date()));
        cheque.setClient(getAuthClient());
        chequeRepository.save(cheque);

        for (Map.Entry<Long, Integer> entry : carts.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).get();
            if (entry.getValue() > product.getQuantityInStock()) {
                chequeRepository.delete(cheque);
                return "redirect:/cart";
            } else {
                Product_Cheque position = new Product_Cheque(entry.getValue(),
                        cheque,
                        productRepository.findById(entry.getKey()).get());
                product.setQuantityInStock(product.getQuantityInStock() - entry.getValue());
                productRepository.save(product);
                product_chequeRepository.save(position);
            }
        }

        session.setAttribute("cart", null);

        return "redirect:/cart/clear";
    }
}

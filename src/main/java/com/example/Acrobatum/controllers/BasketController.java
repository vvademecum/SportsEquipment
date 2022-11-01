package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.*;
import com.example.Acrobatum.repositories.*;
import org.aspectj.weaver.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    ChequeRepository chequeRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    Product_ChequeRepository product_chequeRepository;
    @Autowired
    ProductRepository productRepository;

    public Employee getAuthEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findByLogin(authentication.getName());

        return employee;
    }

    @GetMapping
    public String basketsList(@RequestParam(required = false) String sDate, Model model) {
        if (sDate != "") {
            model.addAttribute("baskets", chequeRepository.findByDateOfPurchaseContains(sDate));

        } else {
            model.addAttribute("baskets", chequeRepository.findAll());
        }
        model.addAttribute("positions", product_chequeRepository.findAll());

        return "basket/main";
    }

    static List<Product_Cheque> positions = new ArrayList<>();

    @GetMapping("/add")
    public String basketAddPage(@ModelAttribute("basket")
                                @Valid Сheque basket,
                                BindingResult bindingResult,
                                Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("dateNow", dateFormat.format(new Date()));
        model.addAttribute("basket", new Сheque());
        model.addAttribute("EmpId", getAuthEmployee().getId());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("positions", positions);
        model.addAttribute("position", new Product_Cheque());

        return "basket/add";
    }

    @PostMapping("/add")
    public String createBasket(@ModelAttribute("basket")
                               @Valid Сheque basket,
                               BindingResult bindingResult, Model model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (bindingResult.hasErrors()) {
            model.addAttribute("dateNow", dateFormat.format(new Date()));
            model.addAttribute("basket", new Сheque());
            model.addAttribute("EmpId", getAuthEmployee().getId());
            model.addAttribute("employees", employeeRepository.findAll());
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("products", productRepository.findAll());

            model.addAttribute("positions", positions);
            model.addAttribute("position", new Product_Cheque());
            model.addAttribute("basket", basket);
            return "basket/add";
        }

        chequeRepository.save(basket);

        return "redirect:/basket";
    }

    @GetMapping("/addProduct")
    public String addProductToBasket(@ModelAttribute("position")
                                     Сheque basket,
                                     Product_Cheque position,
                                     BindingResult bindingResult,
                                     Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("dateNow", dateFormat.format(new Date()));
        model.addAttribute("basket", basket);
        model.addAttribute("EmpId", getAuthEmployee().getId());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
       // model.addAttribute("positions", product_chequeRepository.findAll());

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        model.addAttribute("position", position);

        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct == null || posWithProduct.size() == 0) {

            Product_Cheque dbPosition = new Product_Cheque(position.getQuantity(),
                    basket,
                    position.getProduct());
            product_chequeRepository.save(dbPosition);
        }else {
            posWithProduct.get(0).setQuantity(posWithProduct.get(0).getQuantity() + position.getQuantity());
            product_chequeRepository.save(posWithProduct.get(0));
        }

        model.addAttribute("positions", product_chequeRepository.findByCheque(basket));

        return "basket/add";
    }

    @GetMapping("/removeProduct")
    public String removeProductFromBasket(@ModelAttribute("position")
                                     Сheque basket,
                                     Product_Cheque position,
                                     BindingResult bindingResult,
                                     Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("dateNow", dateFormat.format(new Date()));
        model.addAttribute("basket", basket);
        model.addAttribute("EmpId", getAuthEmployee().getId());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("position", position);

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct.size() != 0) {
            product_chequeRepository.deleteAll(posWithProduct);
        }

        model.addAttribute("positions", product_chequeRepository.findByCheque(basket));

        return "basket/add";
    }

    @PostMapping("/delete")
    public String basketDelete(@RequestParam long basket_id) {
        Сheque cheque = chequeRepository.findById(basket_id).get();
        if (cheque != null) {
            chequeRepository.delete(cheque);
        }
        return "redirect:/basket";
    }

    @GetMapping("/edit")
    public String basketEditPage(@RequestParam long basket_id, Model model) {

        Сheque basket = chequeRepository.findById(basket_id).get();
        model.addAttribute("basket", basket);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("dateOfPurchase", basket.getDateOfPurchase());

        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("positions", product_chequeRepository.findByCheque(basket));
        model.addAttribute("position", new Product_Cheque());

        return "basket/edit";
    }

    @GetMapping("/addProductEdit")
    public String addProductToBasketEdit(@ModelAttribute("position")
                                     Сheque basket,
                                     Product_Cheque position,
                                     BindingResult bindingResult,
                                     Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("dateNow", dateFormat.format(new Date()));
        model.addAttribute("basket", basket);
        model.addAttribute("EmpId", getAuthEmployee().getId());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        // model.addAttribute("positions", product_chequeRepository.findAll());

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        model.addAttribute("position", position);

        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct == null || posWithProduct.size() == 0) {

            Product_Cheque dbPosition = new Product_Cheque(position.getQuantity(),
                    basket,
                    position.getProduct());
            product_chequeRepository.save(dbPosition);
        }else {
            posWithProduct.get(0).setQuantity(posWithProduct.get(0).getQuantity() + position.getQuantity());
            product_chequeRepository.save(posWithProduct.get(0));
        }

        model.addAttribute("positions", product_chequeRepository.findByCheque(basket));

        return "basket/edit";
    }

    @GetMapping("/removeProductEdit")
    public String removeProductFromBasketEdit(@ModelAttribute("position")
                                          Сheque basket,
                                          Product_Cheque position,
                                          BindingResult bindingResult,
                                          Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("dateNow", dateFormat.format(new Date()));
        model.addAttribute("basket", basket);
        model.addAttribute("EmpId", getAuthEmployee().getId());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("position", position);

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct.size() != 0) {
            product_chequeRepository.deleteAll(posWithProduct);
        }

        model.addAttribute("positions", product_chequeRepository.findByCheque(basket));

        return "basket/edit";
    }

    @PostMapping("/edit")
    public String clientEdit(@ModelAttribute("basket")
                             @Valid Сheque basket,
                             BindingResult bindingResult, Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (bindingResult.hasErrors()) {
            model.addAttribute("dateNow", dateFormat.format(new Date()));
            model.addAttribute("basket", new Сheque());
            model.addAttribute("EmpId", getAuthEmployee().getId());
            model.addAttribute("employees", employeeRepository.findAll());
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("products", productRepository.findAll());

            model.addAttribute("positions", positions);
            model.addAttribute("position", new Product_Cheque());
            model.addAttribute("basket", basket);
            return "basket/add";
        }

        chequeRepository.save(basket);

        return "redirect:/basket";
    }
}

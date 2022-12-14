package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Employee;
import com.example.Acrobatum.models.Product_Cheque;
import com.example.Acrobatum.models.Сheque;
import com.example.Acrobatum.repositories.*;
import com.example.Acrobatum.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {

    final ChequeRepository chequeRepository;
    final ClientRepository clientRepository;
    final EmployeeRepository employeeRepository;
    final Product_ChequeRepository product_chequeRepository;
    final ProductRepository productRepository;

    public BasketController(ChequeRepository chequeRepository, ClientRepository clientRepository, EmployeeRepository employeeRepository, Product_ChequeRepository product_chequeRepository, ProductRepository productRepository) {
        this.chequeRepository = chequeRepository;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.product_chequeRepository = product_chequeRepository;
        this.productRepository = productRepository;
    }

    public Employee getAuthEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findByLogin(authentication.getName());

        return employee;
    }

    public Client getAuthClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.findByLogin(authentication.getName());

        return client;
    }

    @GetMapping
    public String basketsList(@RequestParam(required = false) String sDate, Model model) {
        if (AuthService.getRole().equals("ROLE_USER"))
            if (sDate != "")
                model.addAttribute("baskets", chequeRepository.findByDateOfPurchaseContainsAndClient(sDate, getAuthClient()));
            else
                model.addAttribute("baskets", chequeRepository.findByClient(getAuthClient()));
        else if (sDate != "")
            model.addAttribute("baskets", chequeRepository.findByDateOfPurchaseContains(sDate));
        else
            model.addAttribute("baskets", chequeRepository.findAll());

        model.addAttribute("positions", product_chequeRepository.findAll());

        return "basket/main";
    }

    static List<Product_Cheque> positions = new ArrayList<>();

    @GetMapping("/add")
    public String basketAddPage(@ModelAttribute("basket")
                                Сheque basket,
                                Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("basket", new Сheque());
        model.addAttribute("dateNow", dateFormat.format(new Date()));
        model.addAttribute("empId", getAuthEmployee() != null ? getAuthEmployee().getId() : 7);
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
            model.addAttribute("empId", getAuthEmployee() != null ? getAuthEmployee().getId() : 7);
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
        model.addAttribute("empId", getAuthEmployee() != null ? getAuthEmployee().getId() : 7);
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        model.addAttribute("position", position);

        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct == null || posWithProduct.size() == 0) {
            Product_Cheque dbPosition = new Product_Cheque(position.getQuantity(),
                    basket,
                    position.getProduct());
            product_chequeRepository.save(dbPosition);
        } else {
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
        model.addAttribute("empId", getAuthEmployee() != null ? getAuthEmployee().getId() : 7);
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("position", position);

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct.size() != 0) {
            if (posWithProduct.get(0).getQuantity() > position.getQuantity()) {
                posWithProduct.get(0).setQuantity(posWithProduct.get(0).getQuantity() - position.getQuantity());
                product_chequeRepository.save(posWithProduct.get(0));
            } else
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Сheque basket = chequeRepository.findById(basket_id).get();
        model.addAttribute("basket", basket);
        model.addAttribute("dateOfPurchase", basket.getDateOfPurchase().split(" ")[0]);
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
        model.addAttribute("empId", getAuthEmployee() != null ? getAuthEmployee().getId() : 7);
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        model.addAttribute("position", position);

        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct == null || posWithProduct.size() == 0) {

            Product_Cheque dbPosition = new Product_Cheque(position.getQuantity(),
                    basket,
                    position.getProduct());
            product_chequeRepository.save(dbPosition);
        } else {
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
        model.addAttribute("empId", getAuthEmployee() != null ? getAuthEmployee().getId() : 7);
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        model.addAttribute("position", position);

        basket.setDateOfPurchase(dateFormat.format(new Date()));
        chequeRepository.save(basket);
        List<Product_Cheque> posWithProduct = product_chequeRepository.findByProductAndCheque(position.getProduct(), basket);
        if (posWithProduct.size() != 0) {
            if (posWithProduct.get(0).getQuantity() > position.getQuantity()) {
                posWithProduct.get(0).setQuantity(posWithProduct.get(0).getQuantity() - position.getQuantity());
                product_chequeRepository.save(posWithProduct.get(0));
            } else
                product_chequeRepository.deleteAll(posWithProduct);
        }

        model.addAttribute("positions", product_chequeRepository.findByCheque(basket));

        return "basket/edit";
    }

    @PostMapping("/edit")
    public String basketEdit(@ModelAttribute("basket")
                             @Valid Сheque basket,
                             BindingResult bindingResult,
                             BindingResult bindingResult1,
                             Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (bindingResult.hasErrors() || bindingResult1.hasErrors()) {
            model.addAttribute("dateNow", dateFormat.format(new Date()));
            model.addAttribute("basket", new Сheque());
            model.addAttribute("empId", getAuthEmployee() != null ? getAuthEmployee().getId() : 7);
            model.addAttribute("employees", employeeRepository.findAll());
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("products", productRepository.findAll());

            model.addAttribute("positions", positions);
            model.addAttribute("position", new Product_Cheque());
            model.addAttribute("basket", basket);
            return "basket/edit";
        }

        chequeRepository.save(basket);

        return "redirect:/basket";
    }
}

package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.*;
import com.example.Acrobatum.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    CharacteristicsRepository characteristicsRepository;
    @Autowired
    KindOfSportRepository kindOfSportRepository;
    @Autowired
    GenderRepository genderRepository;
    @Autowired
    SeasonRepository seasonRepository;
    @Autowired
    CountryRepository countryRepository;

    @GetMapping
    public String productList(@RequestParam(required = false) String sName, Model model) {
        if (sName != "") {
            model.addAttribute("products", productRepository.findByNameContains(sName));
        } else {
            model.addAttribute("products", productRepository.findAll());
        }
        //model.addAttribute("providers", providerRepository.findAll());

        return "product/main";
    }

    @GetMapping("/add")
    public String productAddPage(@ModelAttribute("product")
                                 Product product,
                                 Model model) {

        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("characteristics", new Characteristics());
        model.addAttribute("kindsOfSport", kindOfSportRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("genders", genderRepository.findAll());
        model.addAttribute("seasons", seasonRepository.findAll());

        return "product/add";
    }

    @PostMapping("/add")
    public String productAdd(@ModelAttribute("product")
                             @Valid Product product,
                             BindingResult bindingResult,
                             Characteristics characteristics,
                             Model model) {

        Product dbProduct = productRepository.findByName(product.getName());
        if (dbProduct != null) {
            model.addAttribute("providers", providerRepository.findAll());
            model.addAttribute("kindsOfSport", kindOfSportRepository.findAll());
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("genders", genderRepository.findAll());
            model.addAttribute("seasons", seasonRepository.findAll());
            model.addAttribute("product", product);
            model.addAttribute("message", "Такой продукт уже существует");
            return "product/add";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("providers", providerRepository.findAll());
            model.addAttribute("kindsOfSport", kindOfSportRepository.findAll());
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("genders", genderRepository.findAll());
            model.addAttribute("seasons", seasonRepository.findAll());
            model.addAttribute("product", product);
            return "product/add";
        }

        Characteristics dbCharacteristics = new Characteristics(
                characteristics.getGuaranteePeriod(),
                characteristics.getKindOfSport(),
                characteristics.getSeason(),
                characteristics.getGender(),
                characteristics.getCountry()
        );
        characteristicsRepository.save(dbCharacteristics);

        dbProduct = new Product(product.getName(),
                product.getDescription(),
                product.getQuantityInStock(),
                product.getCategory(),
                product.getCost(),
                product.getProvider(),
                dbCharacteristics
        );
        productRepository.save(dbProduct);

        return "redirect:/product";
    }

    @PostMapping("/delete")
    public String productDelete(@RequestParam long product_id) {
        Product product = productRepository.findById(product_id).get();
        if (product != null) {
            productRepository.delete(product);
        }
        return "redirect:/product";
    }

    @GetMapping("/edit")
    public String productEditPage(@RequestParam long product_id, Model model) {

        Product product = productRepository.findById(product_id).get();
        Characteristics characteristics = characteristicsRepository.findByProductId(product_id);
        model.addAttribute("product", product);
        model.addAttribute("characteristics", characteristics);
        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("kindsOfSport", kindOfSportRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("genders", genderRepository.findAll());
        model.addAttribute("seasons", seasonRepository.findAll());

        return "product/edit";
    }

    @PostMapping("/edit")
    public String employeeEdit(@ModelAttribute("product")
                               @Valid Product product,
                               BindingResult bindingResult,
                               Characteristics characteristics,
                               Model model) {

        Product dbProduct = productRepository.findByName(product.getName());
        if ((dbProduct != null && dbProduct.getId() != dbProduct.getId())) {
            model.addAttribute("product", product);
            model.addAttribute("characteristics", characteristics);
            model.addAttribute("providers", providerRepository.findAll());
            model.addAttribute("kindsOfSport", kindOfSportRepository.findAll());
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("genders", genderRepository.findAll());
            model.addAttribute("seasons", seasonRepository.findAll());
            model.addAttribute("message", "Такой товар уже существует");
            return "product/edit";
        }
        dbProduct = productRepository.findById(product.getId()).get();

        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("characteristics", characteristics);
            model.addAttribute("providers", providerRepository.findAll());
            model.addAttribute("kindsOfSport", kindOfSportRepository.findAll());
            model.addAttribute("countries", countryRepository.findAll());
            model.addAttribute("genders", genderRepository.findAll());
            model.addAttribute("seasons", seasonRepository.findAll());
            return "product/edit";
        }

        Characteristics dbCharacteristics = product.getCharacteristics();
        dbCharacteristics.setGuaranteePeriod(characteristics.getGuaranteePeriod());
        dbCharacteristics.setKindOfSport(characteristics.getKindOfSport());
        dbCharacteristics.setSeason(characteristics.getSeason());
        dbCharacteristics.setGender(characteristics.getGender());
        dbCharacteristics.setCountry(characteristics.getCountry());
        characteristicsRepository.save(dbCharacteristics);

        dbProduct.setName(product.getName());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setQuantityInStock(product.getQuantityInStock());
        dbProduct.setCategory(product.getCategory());
        dbProduct.setCost(product.getCost());
        dbProduct.setProvider(product.getProvider());
        dbProduct.setCharacteristics(dbCharacteristics);
        productRepository.save(dbProduct);

        return "redirect:/product";
    }

}

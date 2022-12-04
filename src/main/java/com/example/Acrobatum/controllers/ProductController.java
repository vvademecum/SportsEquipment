package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Characteristics;
import com.example.Acrobatum.models.Product;
import com.example.Acrobatum.repositories.*;
import com.example.Acrobatum.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    final ProductRepository productRepository;
    final ProviderRepository providerRepository;
    final CharacteristicsRepository characteristicsRepository;
    final KindOfSportRepository kindOfSportRepository;
    final GenderRepository genderRepository;
    final SeasonRepository seasonRepository;
    final CountryRepository countryRepository;

    public ProductController(ProductService productService, ProductRepository productRepository, ProviderRepository providerRepository, CharacteristicsRepository characteristicsRepository, KindOfSportRepository kindOfSportRepository, GenderRepository genderRepository, SeasonRepository seasonRepository, CountryRepository countryRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.characteristicsRepository = characteristicsRepository;
        this.kindOfSportRepository = kindOfSportRepository;
        this.genderRepository = genderRepository;
        this.seasonRepository = seasonRepository;
        this.countryRepository = countryRepository;
    }

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

    @Value("${productsPhoto.path}")
    private String uploadPath;

    @PostMapping("/add")
    public String productAdd(
                            @RequestParam("file") MultipartFile file,
                            @ModelAttribute("product")
                            @Valid Product product,
                            BindingResult bindingResult,
                            @Valid Characteristics characteristics,
                            BindingResult bindingResult2,
                            Model model) throws IOException {

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
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
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

        productService.saveProduct(dbProduct, file);

//        if (file != null) {
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists())
//                uploadDir.mkdir();
//            String uuidFile = UUID.randomUUID().toString();
//            String resultFile = product.getName() + uuidFile + "." + file.getOriginalFilename().split("\\.")[1];
//
//            try {
//                file.transferTo(new File(uploadDir + "/" + resultFile));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            dbProduct.setPhoto(resultFile);
//        }
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

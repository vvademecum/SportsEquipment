package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Characteristics;
import com.example.Acrobatum.models.Image;
import com.example.Acrobatum.models.Product;
import com.example.Acrobatum.models.Product_Cheque;
import com.example.Acrobatum.repositories.*;
import com.example.Acrobatum.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.Acrobatum.controllers.CartController.carts;

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
    final ImageRepository imageRepository;
    final Product_ChequeRepository positionRepository;

    public ProductController(ProductService productService, ProductRepository productRepository, ProviderRepository providerRepository, CharacteristicsRepository characteristicsRepository, KindOfSportRepository kindOfSportRepository, GenderRepository genderRepository, SeasonRepository seasonRepository, CountryRepository countryRepository, ImageRepository imageRepository, Product_ChequeRepository product_chequeRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.characteristicsRepository = characteristicsRepository;
        this.kindOfSportRepository = kindOfSportRepository;
        this.genderRepository = genderRepository;
        this.seasonRepository = seasonRepository;
        this.countryRepository = countryRepository;
        this.imageRepository = imageRepository;
        this.positionRepository = product_chequeRepository;
    }

    @GetMapping
    public String productList(@RequestParam(required = false) String sName, Model model) {
        if (sName != "") {
            model.addAttribute("products", productRepository.findByNameContains(sName));
        } else {
            model.addAttribute("products", productRepository.findAll());
        }

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

    @GetMapping("/selectedProduct")
    public String getOneProduct(@RequestParam(required = false) String text,
                                @RequestParam long id, Model model, HttpSession session) {

        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        Characteristics characteristics = characteristicsRepository.findByProductId(id);
        model.addAttribute("characteristics", characteristics);

        if (session.getAttribute("cart") == null)
            model.addAttribute("carts", null);
        else
            model.addAttribute("carts", carts.get(id));

        return "product/productPage";
    }

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

        Characteristics dbCharacteristics = createCharacteristics(characteristics);
        createProduct(product, dbCharacteristics, file);

        return "redirect:/product";
    }

    Characteristics createCharacteristics(Characteristics characteristics) {
        Characteristics dbCharacteristics = new Characteristics(
                characteristics.getGuaranteePeriod(),
                characteristics.getKindOfSport(),
                characteristics.getSeason(),
                characteristics.getGender(),
                characteristics.getCountry()
        );
        characteristicsRepository.save(dbCharacteristics);
        return dbCharacteristics;
    }

    void createProduct(Product product, Characteristics dbCharacteristics, MultipartFile file) throws IOException {
        Product dbProduct = new Product(product.getName(),
                product.getDescription(),
                product.getQuantityInStock(),
                product.getCategory(),
                product.getCost(),
                product.getProvider(),
                dbCharacteristics
        );

        productService.saveProduct(dbProduct, file);
        productRepository.save(dbProduct);
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
        if (imageRepository.findByProductId(product_id) != null)
            model.addAttribute("image", imageRepository.findByProductId(product_id));
        model.addAttribute("characteristics", characteristics);
        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("kindsOfSport", kindOfSportRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("genders", genderRepository.findAll());
        model.addAttribute("seasons", seasonRepository.findAll());

        return "product/edit";
    }

    @PostMapping("/edit")
    public String productEdit(@RequestParam("file") MultipartFile file,
                              @ModelAttribute("product")
                              @Valid Product product,
                              @Valid Image image,
                              BindingResult bindingResult,
                              BindingResult bindingResult1,
                              Characteristics characteristics,
                              Model model) throws IOException {

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

        productService.saveProduct(dbProduct, file);
        productRepository.save(dbProduct);

        return "redirect:/product";
    }

    @GetMapping("/chart")
    public String showChart(Model model) {

        Map<String, Integer> positions = new HashMap<>();

        positionRepository.findAll().forEach(p -> {
            if (positions.containsKey(p.getProduct().getName()))
                positions.put(p.getProduct().getName(), positions.get(p.getProduct().getName()) + p.getQuantity());
            else
                positions.put(p.getProduct().getName(), p.getQuantity());
        });

//        for (Map.Entry<String, Integer> entry : positions.entrySet()) {
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//        }

        model.addAttribute("products", (positions
                                                    .entrySet().stream()
                                                    .map(Map.Entry::getKey)
                                                    .collect(Collectors.toList())));
        model.addAttribute("sales", (positions.values()
                                                .stream()
                                                .collect(Collectors.toList())));
        return "product/ordersChart";
    }
}

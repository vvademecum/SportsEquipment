package com.example.Acrobatum.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

@Entity
public class Product {
    public Product(String name,
                   String description,
                   int quantityInStock,
                   String category,
                   double cost,
                   Provider provider,
                   Characteristics characteristics) {
        this.name = name;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.category = category;
        this.cost = cost;
        this.provider = provider;
        this.characteristics = characteristics;
    }

    public Product(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 100, message = "Заполните поле на 1-100 символа")
    private String name;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 100, message = "Заполните поле на 1-100 символа")
    private String description;
    @NotNull
    @PositiveOrZero(message = "Значение должно быть положительным или равным нулю")
    private int quantityInStock;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "Заполните поле на 1-50 символа")
    private String category;
    @NotNull
    @Positive(message = "Значение должно быть положительным")
    private double cost;
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    private Provider provider;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristics_id")
    private Characteristics characteristics;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;

//    private String photo;

//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] photo;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product_Cheque> product_chequeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Characteristics characteristics) {
        this.characteristics = characteristics;
    }

    public List<Product_Cheque> getProduct_chequeList() {
        return product_chequeList;
    }

    public void setProduct_chequeList(List<Product_Cheque> product_chequeList) {
        this.product_chequeList = product_chequeList;
    }

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

}

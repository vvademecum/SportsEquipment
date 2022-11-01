package com.example.Acrobatum.models;

import javax.persistence.*;
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

    private String name;
    private String description;
    private int quantityInStock;
    private String category;
    private double cost;
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    private Provider provider;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristics_id")
    private Characteristics characteristics;

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
}

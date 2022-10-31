package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collector;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String surname;
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

}

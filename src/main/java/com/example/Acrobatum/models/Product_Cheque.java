package com.example.Acrobatum.models;

import javax.persistence.*;

@Entity
public class Product_Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
    private Сheque cheque;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;


}

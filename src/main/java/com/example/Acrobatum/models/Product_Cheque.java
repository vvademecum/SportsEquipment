package com.example.Acrobatum.models;

import javax.persistence.*;

@Entity
public class Product_Cheque {

    public Product_Cheque(int quantity, Сheque cheque, Product product) {
        this.quantity = quantity;
        this.cheque = cheque;
        this.product = product;
    }

    public Product_Cheque(){}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Сheque getCheque() {
        return cheque;
    }

    public void setCheque(Сheque cheque) {
        this.cheque = cheque;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

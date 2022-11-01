package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Сheque {

    public Сheque(String dateOfPurchase, Client client, Employee employee) {
        this.dateOfPurchase = dateOfPurchase;
        this.client = client;
        this.employee = employee;
    }

    public Сheque(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dateOfPurchase;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "cheque", cascade = CascadeType.REMOVE)
    private List<Product_Cheque> product_chequeList;

    public List<Product_Cheque> getProduct_chequeList() {
        return product_chequeList;
    }

    public void setProduct_chequeList(List<Product_Cheque> product_chequeList) {
        this.product_chequeList = product_chequeList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}

package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Сheque {

    public Сheque(Date dateOfPurchase, Client client, Employee employee) {
        this.dateOfPurchase = dateOfPurchase;
        this.client = client;
        this.employee = employee;
    }

    public Сheque(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPurchase;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "cheque", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product_Cheque> product_chequeList;

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
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

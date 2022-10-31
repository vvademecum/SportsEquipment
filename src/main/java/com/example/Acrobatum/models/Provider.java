package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Provider {
    public Provider(String orgName) {
        this.orgName = orgName;
    }
    public Provider(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orgName;
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "contacts_id")
    private Contacts contacts;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> productList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

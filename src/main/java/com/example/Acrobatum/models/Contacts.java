package com.example.Acrobatum.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Pattern(regexp = "^\\+7[0-9]{10}$", message = "Телефон должен иметь вид +72223334455")
    private String phoneNumber;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 5, max = 100, message = "Заполните поле на 5-100 символа")
    private String email;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 5, max = 200, message = "Заполните поле на 5-200 символа")
    private String address;
    @OneToOne(optional = true, mappedBy = "contacts")
    private Client client;
    @OneToOne(optional = true, mappedBy = "contacts")
    private Provider provider;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}

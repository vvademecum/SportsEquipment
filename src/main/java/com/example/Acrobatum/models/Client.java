package com.example.Acrobatum.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Client {

    public Client(String surname, String name, String patronymic, String login, String password) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
    }

    public Client(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "Заполните поле на 1-50 символов")
    private String surname;
    @NotNull
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "Заполните поле на 1-50 символов")
    private String name;
    @NotNull
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "Заполните поле на 1-50 символов")
    private String patronymic;
    @Column(unique = true)
    @NotEmpty(message = "Поле не может быть пустым")
    private String login;
    private String password;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "contacts_id")
    private Contacts contacts;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Сheque> checkList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }
}

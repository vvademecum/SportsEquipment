package com.example.Acrobatum.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Employee {

    public Employee(String surname, String name, String patronymic, String seriesPassport, String numberPassport, String email, String login, String password, Role role) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.seriesPassport = seriesPassport;
        this.numberPassport = numberPassport;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Employee(){

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
    @NotEmpty(message = "Поле не можнт быть пустым")
    @Size(min = 1, max = 50, message = "Заполните поле на 1-50 символов")
    private String patronymic;
    @NotNull
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 4, max = 4, message = "Заполните поле на 4 символа")
    private String seriesPassport;
    @NotNull
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 6, max = 6, message = "Заполните поле на 6 символа")
    private String numberPassport;
    @NotNull
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 5, max = 100, message = "Заполните поле на 5-100 символа")
    private String email;
    @Column(unique = true)
    @NotEmpty(message = "Поле не может быть пустым")
    private String login;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
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

    public String getSeriesPassport() {
        return seriesPassport;
    }

    public void setSeriesPassport(String seriesPassport) {
        this.seriesPassport = seriesPassport;
    }

    public String getNumberPassport() {
        return numberPassport;
    }

    public void setNumberPassport(String numberPassport) {
        this.numberPassport = numberPassport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

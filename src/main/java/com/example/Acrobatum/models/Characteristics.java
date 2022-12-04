package com.example.Acrobatum.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Characteristics {
    public Characteristics(String guaranteePeriod, KindOfSport kindOfSport, Season season, Gender gender, Country country) {
        this.guaranteePeriod = guaranteePeriod;
        this.kindOfSport = kindOfSport;
        this.season = season;
        this.gender = gender;
        this.country = country;
    }
    public Characteristics(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 100, message = "Заполните поле на 1-100 символа")
    private String guaranteePeriod;
    @OneToOne(optional = true, mappedBy = "characteristics")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "kindOfSport_id", referencedColumnName = "id")
    private KindOfSport kindOfSport;
    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    private Season season;
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public KindOfSport getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(KindOfSport kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

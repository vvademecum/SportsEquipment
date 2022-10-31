package com.example.Acrobatum.models;

import javax.persistence.*;

@Entity
public class Characteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
}

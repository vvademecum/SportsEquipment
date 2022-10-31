package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String country;

    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Characteristics> characteristics;
}

package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class KindOfSport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String kindOfSport;

    @OneToMany(mappedBy = "kindOfSport", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Characteristics> characteristics;
}

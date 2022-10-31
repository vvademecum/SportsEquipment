package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String season;

    @OneToMany(mappedBy = "season", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Characteristics> characteristics;
}

package com.example.Acrobatum.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String gender;

    @OneToMany(mappedBy = "gender", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Characteristics> characteristics;
}

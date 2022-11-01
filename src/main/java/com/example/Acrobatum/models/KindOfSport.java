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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public List<Characteristics> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristics> characteristics) {
        this.characteristics = characteristics;
    }
}

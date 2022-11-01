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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}

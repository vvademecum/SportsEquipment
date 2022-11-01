package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Characteristics;
import org.springframework.data.repository.CrudRepository;

public interface CharacteristicsRepository extends CrudRepository<Characteristics, Long> {

    Characteristics findByProductId(Long product_id);

}

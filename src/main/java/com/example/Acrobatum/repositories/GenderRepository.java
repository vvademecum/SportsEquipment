package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Gender;
import com.example.Acrobatum.models.KindOfSport;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<Gender, Long> {

}

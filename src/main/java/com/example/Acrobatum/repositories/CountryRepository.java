package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Country;
import com.example.Acrobatum.models.Gender;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {

}

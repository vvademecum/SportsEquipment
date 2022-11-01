package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.KindOfSport;
import com.example.Acrobatum.models.Season;
import org.springframework.data.repository.CrudRepository;

public interface SeasonRepository extends CrudRepository<Season, Long> {
}

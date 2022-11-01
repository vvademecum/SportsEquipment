package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Сheque;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChequeRepository extends CrudRepository<Сheque, Long> {

    List<Сheque> findByDateOfPurchaseContains(String date);
}

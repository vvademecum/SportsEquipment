package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Сheque;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChequeRepository extends CrudRepository<Сheque, Long> {

    List<Сheque> findByDateOfPurchaseContains(String date);
    List<Сheque> findByDateOfPurchaseContainsAndClient(String date, Client client);
    List<Сheque> findByClient(Client client);

}

package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Product;
import com.example.Acrobatum.models.Product_Cheque;
import com.example.Acrobatum.models.Сheque;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Product_ChequeRepository extends CrudRepository<Product_Cheque, Long> {

    List<Product_Cheque> findByCheque(Сheque cheque);

    List<Product_Cheque> findByProductAndCheque(Product product, Сheque cheque);

}

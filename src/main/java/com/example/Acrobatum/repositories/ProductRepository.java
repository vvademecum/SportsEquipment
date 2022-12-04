package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Image;
import com.example.Acrobatum.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameContains(String productName);
    Product findByName(String productName);

    //Image findById(Long id);
    //List<Product> findByName(String title);

}

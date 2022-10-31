package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Long> {

    Provider findByOrgName(String orgName);
    List<Provider> findByOrgNameContains(String orgName);
}

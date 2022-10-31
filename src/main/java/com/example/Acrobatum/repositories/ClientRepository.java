package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Employee;
import com.example.Acrobatum.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByLogin(String login);
    List<Client> findByNameContainsOrSurnameContainsOrPatronymicContains(String name, String surname, String patronymic);


}

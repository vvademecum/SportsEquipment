package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByLogin(String login);
    List<Employee> findByNameContainsOrSurnameContainsOrPatronymicContains(String name, String surname, String patronymic);

}

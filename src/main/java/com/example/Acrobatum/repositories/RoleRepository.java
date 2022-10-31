package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);

}

package com.example.Acrobatum.service;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Employee;
import com.example.Acrobatum.repositories.ClientRepository;
import com.example.Acrobatum.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String role = "";
        UserDetails userDetails = null;
        Client client = clientRepository.findByLogin(username);
        Employee employee = employeeRepository.findByLogin(username);

        if(client != null) {
            role = "USER";
            userDetails = User.builder()
                    .username(client.getLogin())
                    .password(client.getPassword())
                    .roles(role)
                    .build();
        }
        if(employee != null) {
            role = employee.getRole().getRole();
            userDetails = User.builder()
                    .username(employee.getLogin())
                    .password(employee.getPassword())
                    .roles(role)
                    .build();
        }

        return userDetails;
    }
}

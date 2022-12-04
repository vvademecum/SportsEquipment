package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.repositories.ClientRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    final ClientRepository clientRepository;

    public RegistrationController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String registrationPage(@ModelAttribute("client") Client client, Model model) {
        model.addAttribute("client", client);
        return "registration";
    }

    @PostMapping
    public String registrationClient(@ModelAttribute("client") Client client) {
        client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
        client.setLogin(client.getLogin());
        client.setSurname(client.getSurname());
        client.setName(client.getName());
        client.setPatronymic(client.getPatronymic());
        clientRepository.save(client);
        return "redirect:/logIn";
    }
}

package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Client;
import com.example.Acrobatum.models.Contacts;
import com.example.Acrobatum.repositories.ClientRepository;
import com.example.Acrobatum.repositories.ContactsRepository;
import com.example.Acrobatum.service.ClientExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ResourceLoader resourceLoader;
    private RuntimeException runtimeException;
    final ClientRepository clientRepository;
    final ContactsRepository contactsRepository;

    public ClientController(ClientRepository clientRepository, ContactsRepository contactsRepository) {
        this.clientRepository = clientRepository;
        this.contactsRepository = contactsRepository;
    }

    @GetMapping
    public String clientList(@RequestParam(required = false) String sName,
                             @RequestParam(required = false) String sSurname,
                             @RequestParam(required = false) String sPatronymic, Model model) {
        if (sName != "" || sSurname != "" || sPatronymic != "") {
            sSurname = sSurname == "" ? "---" : sSurname;
            sName = sName == "" ? "---" : sName;
            sPatronymic = sPatronymic == "" ? "---" : sPatronymic;
            model.addAttribute("clients", clientRepository.findByNameContainsOrSurnameContainsOrPatronymicContains(sName, sSurname, sPatronymic));
            model.addAttribute("contacts", contactsRepository.findAll());

        } else {
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("contacts", contactsRepository.findAll());
        }
        return "client/main";
    }

    @GetMapping("/add")
    public String clientAddPage(@ModelAttribute("client")
                                Client client,
                                BindingResult bindingResult,
                                Model model) {

        model.addAttribute("client", new Client());
        model.addAttribute("contacts", new Contacts());
        return "client/add";
    }

    @PostMapping("/add")
    public String createClient(@ModelAttribute("client")
                               @Valid Client client,
                               BindingResult bindingResult,
                               @Valid Contacts contacts,
                               BindingResult bindingResult2,
                               @RequestParam(required = false) Boolean hasContacts,
                               Model model) {

        Client dbClient = clientRepository.findByLogin(client.getLogin());
        if (dbClient != null) {
            model.addAttribute("client", client);
            model.addAttribute("contacts", contacts);
            model.addAttribute("message", "Такой логин уже существует");
            return "client/add";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            return "client/add";
        }
        if (hasContacts == null ? false : hasContacts && bindingResult2.hasErrors()) {
            model.addAttribute("client", client);
            return "client/add";
        }

        dbClient = new Client(client.getName(),
                client.getSurname(),
                client.getPatronymic(),
                client.getLogin(),
                new BCryptPasswordEncoder().encode(client.getPassword()));

        if (hasContacts == null ? false : hasContacts) {
            Contacts dbContacts = new Contacts();
            dbContacts.setAddress(contacts.getAddress());
            dbContacts.setPhoneNumber(contacts.getPhoneNumber());
            dbContacts.setEmail(contacts.getEmail());

            dbClient.setContacts(dbContacts);
        } else
            dbClient.setContacts(null);


        clientRepository.save(dbClient);

        return "redirect:/client";
    }

    @PostMapping("/delete")
    public String clientDelete(@RequestParam long client_id) {
        Client client = clientRepository.findById(client_id).get();
        if (client != null)
            clientRepository.delete(client);
        return "redirect:/client";
    }

    @GetMapping("/edit")
    public String clientEditPage(@RequestParam long client_id, Model model) {

        Client client = clientRepository.findById(client_id).get();
        model.addAttribute("client", client);
        model.addAttribute("contacts", client.getContacts() != null ? client.getContacts() : new Contacts());

        return "client/edit";
    }

    @PostMapping("/edit")
    public String clientEdit(@ModelAttribute("client")
                             @Valid Client client,
                             BindingResult bindingResult,
                             @Valid Contacts contacts,
                             BindingResult bindingResult2,
                             @RequestParam(required = false) String new_password,
                             @RequestParam(required = false) Boolean hasContacts, Model model) {

        Client dbClient = clientRepository.findByLogin(client.getLogin());
        if ((dbClient != null && dbClient.getId() != client.getId())) {
            model.addAttribute("message", "Такой логин уже существует");
            return "client/edit";
        }
        dbClient = clientRepository.findById(client.getId()).get();
        if (bindingResult.hasErrors()) {
            return "client/edit";
        }
        if (hasContacts == null ? false : hasContacts && bindingResult2.hasErrors()) {
            model.addAttribute("client", client);
            return "client/edit";
        }

        if (hasContacts == null ? false : hasContacts) {
            Contacts dbContacts = dbClient.getContacts() != null ? dbClient.getContacts() : new Contacts();
            dbContacts.setAddress(contacts.getAddress());
            dbContacts.setPhoneNumber(contacts.getPhoneNumber());
            dbContacts.setEmail(contacts.getEmail());

            dbClient.setContacts(dbContacts);
        } else {
            dbClient.setContacts(null);
        }

        if (new_password != null && new_password != "") {
            dbClient.setPassword(new BCryptPasswordEncoder().encode(new_password));
        }
        dbClient.setLogin(client.getLogin());
        dbClient.setName(client.getName());
        dbClient.setSurname(client.getSurname());
        dbClient.setPatronymic(client.getPatronymic());

        clientRepository.save(dbClient);

        return "redirect:/client";
    }

    @GetMapping("/report")
    public ResponseEntity<Resource> getReport() throws IOException {

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = HttpHeaders.CONTENT_DISPOSITION;
        String headerValue = "attachment; filename=clients_" + currentDateTime + ".xlsx";

        List<Client> listClients = (List<Client>) clientRepository.findAll();

        ClientExporter excelExporter = new ClientExporter(listClients);

        excelExporter.export(currentDateTime);

        try {
            String uri = Paths.get("load/clients_" + currentDateTime + ".xlsx").toUri().toString();
            org.springframework.core.io.Resource resource = resourceLoader.getResource(uri);
            ResponseEntity<org.springframework.core.io.Resource> body = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .contentLength(resource.contentLength())
                    .body(resource);
            return body;
        } catch (IOException e) {
            throw runtimeException;
        }
    }
}

package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Contacts;
import com.example.Acrobatum.models.Provider;
import com.example.Acrobatum.repositories.ContactsRepository;
import com.example.Acrobatum.repositories.ProviderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    final ProviderRepository providerRepository;
    final ContactsRepository contactsRepository;

    public ProviderController(ProviderRepository providerRepository, ContactsRepository contactsRepository) {
        this.providerRepository = providerRepository;
        this.contactsRepository = contactsRepository;
    }

    @GetMapping
    public String providerList(@RequestParam(required = false) String sOrgName, Model model) {
        if (sOrgName != "") {
            model.addAttribute("providers", providerRepository.findByOrgNameContains(sOrgName));
            model.addAttribute("contacts", contactsRepository.findAll());
        } else {
            model.addAttribute("providers", providerRepository.findAll());
            model.addAttribute("contacts", contactsRepository.findAll());
        }
        return "provider/main";
    }

    @GetMapping("/add")
    public String providerAddPage(@ModelAttribute("provider")
                                  @Valid Provider provider,
                                  BindingResult bindingResult,
                                  Model model) {

        model.addAttribute("provider", new Provider());
        model.addAttribute("contacts", new Contacts());
        return "provider/add";
    }

    public static Boolean contactsIsntCorrect(Contacts contacts, Boolean hasContacts) {
        if (hasContacts == null ? false : hasContacts) {
            return (contacts.getAddress().isEmpty() ||
                    contacts.getAddress().length() < 5 ||
                    contacts.getAddress().length() > 200 ||
                    contacts.getEmail().isEmpty() ||
                    contacts.getEmail().length() < 5 ||
                    contacts.getEmail().length() > 100 ||
                    contacts.getPhoneNumber().isEmpty() ||
                    !contacts.getPhoneNumber().matches("^\\+7[0-9]{10}$"));
        }
        else
            return false;
    }

    @PostMapping("/add")
    public String createProvider(@ModelAttribute("provider")
                                 @Valid Provider provider,
                                 BindingResult bindingResult,
                                 @Valid Contacts contacts,
                                 BindingResult bindingResult2,
                                 @RequestParam(required = false) Boolean hasContacts,
                                 Model model) {

        Provider dbProvider = providerRepository.findByOrgName(provider.getOrgName());
        if (dbProvider != null) {
            model.addAttribute("provider", provider);
            model.addAttribute("contacts", contacts);
            model.addAttribute("message", "Такая организация уже существует");
            return "provider/add";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("provider", provider);
            model.addAttribute("contacts", contacts);
            return "provider/add";
        }
        if(hasContacts == null ? false : hasContacts && bindingResult2.hasErrors()){
            model.addAttribute("provider", provider);
            model.addAttribute("contacts", contacts);
            return "provider/edit";
        }

        dbProvider = new Provider(provider.getOrgName());

        if (hasContacts == null ? false : hasContacts) {
            Contacts dbContacts = new Contacts();
            dbContacts.setAddress(contacts.getAddress());
            dbContacts.setPhoneNumber(contacts.getPhoneNumber());
            dbContacts.setEmail(contacts.getEmail());

            dbProvider.setContacts(dbContacts);
        } else {
            dbProvider.setContacts(null);
        }

        providerRepository.save(dbProvider);

        return "redirect:/provider";
    }

    @PostMapping("/delete")
    public String providerDelete(@RequestParam long provider_id) {
        Provider provider = providerRepository.findById(provider_id).get();
        if (provider != null) {
            providerRepository.delete(provider);
        }
        return "redirect:/provider";
    }

    @GetMapping("/edit")
    public String providerEditPage(@RequestParam long provider_id, Model model) {

        Provider provider = providerRepository.findById(provider_id).get();
        model.addAttribute("provider", provider);
        model.addAttribute("contacts", provider.getContacts() != null ? provider.getContacts() : new Contacts());

        return "provider/edit";
    }

    @PostMapping("/edit")
    public String providerEdit(@ModelAttribute("provider")
                               @Valid Provider provider,
                               BindingResult bindingResult,
                               @Valid Contacts contacts,
                               BindingResult bindingResult2,
                               @RequestParam(required = false) Boolean hasContacts, Model model) {

        Provider dbProvider = providerRepository.findByOrgName(provider.getOrgName());
        if (dbProvider != null && !dbProvider.getId().equals(provider.getId())) {
            model.addAttribute("provider", provider);
            model.addAttribute("contacts", contacts);
            model.addAttribute("message", "Такая организация уже существует");
            return "provider/edit";
        }
        dbProvider = providerRepository.findById(provider.getId()).get();
        if (bindingResult.hasErrors() || hasContacts == null ? false : hasContacts && bindingResult2.hasErrors()) {
            model.addAttribute("provider", provider);
            model.addAttribute("contacts", contacts);
            return "provider/edit";
        }

        if (hasContacts == null ? false : hasContacts) {
            Contacts dbContacts = dbProvider.getContacts() != null ? dbProvider.getContacts() : new Contacts();
            dbContacts.setAddress(contacts.getAddress());
            dbContacts.setPhoneNumber(contacts.getPhoneNumber());
            dbContacts.setEmail(contacts.getEmail());

            dbProvider.setContacts(dbContacts);
        } else {
            dbProvider.setContacts(null);
        }
        dbProvider.setOrgName(provider.getOrgName());
        providerRepository.save(dbProvider);

        return "redirect:/provider";
    }

}

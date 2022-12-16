package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Employee;
import com.example.Acrobatum.repositories.EmployeeRepository;
import com.example.Acrobatum.repositories.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    final EmployeeRepository employeeRepository;
    final RoleRepository roleRepository;

    public EmployeeController(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String employeeList(@RequestParam(required = false) String sName,
                               @RequestParam(required = false) String sSurname,
                               @RequestParam(required = false) String sPatronymic, Model model) {
        if (sName != "" || sSurname != "" || sPatronymic != "") {
            sSurname = sSurname == "" ? "---" : sSurname;
            sName = sName == "" ? "---" : sName;
            sPatronymic = sPatronymic == "" ? "---" : sPatronymic;
            model.addAttribute("employees", employeeRepository.findByNameContainsOrSurnameContainsOrPatronymicContains(sName, sSurname, sPatronymic));
        } else {
            model.addAttribute("employees", employeeRepository.findAll());
        }
        return "employee/main";
    }

    @GetMapping("/add")
    public String employeeAddPage(@ModelAttribute("employee")
                                  Employee employee,
                                  BindingResult bindingResult,
                                  Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "employee/add";
    }

    @PostMapping("/add")
    public String employeeAdd(@ModelAttribute("employee")
                              @Valid Employee employee,
                              BindingResult bindingResult, Model model) {

        Employee dbEmployee = employeeRepository.findByLogin(employee.getLogin());
        if (dbEmployee != null) {
            model.addAttribute("roles", roleRepository.findAll());
            model.addAttribute("message", "Такой логин уже существует");
            return "employee/add";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "employee/add";
        }

        employee.setSurname(employee.getSurname());
        employee.setName(employee.getName());
        employee.setPatronymic(employee.getPatronymic());
        employee.setEmail(employee.getEmail());
        employee.setRole(employee.getRole());
        employee.setLogin(employee.getLogin());
        employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
        employeeRepository.save(employee);

        return "redirect:/employee";
    }

    @PostMapping("/delete")
    public String employeeDelete(@RequestParam long employee_id) {
        Employee employee = employeeRepository.findById(employee_id).get();
        if (employee != null) {
            employeeRepository.delete(employee);
        }
        return "redirect:/employee";
    }

    @GetMapping("/edit")
    public String employeeEditPage(@RequestParam long employee_id, Model model) {

        Employee employee = employeeRepository.findById(employee_id).get();
        model.addAttribute("employee", employee);
        model.addAttribute("roles", roleRepository.findAll());

        return "employee/edit";
    }

    @PostMapping("/edit")
    public String employeeEdit(@ModelAttribute("employee")
                               @Valid Employee employee,
                               BindingResult bindingResult,
                               @RequestParam(required = false)
                               String new_password, Model model) {

        model.addAttribute("roles", roleRepository.findAll());
        Employee dbEmployee = employeeRepository.findByLogin(employee.getLogin());
        if ((dbEmployee != null && !dbEmployee.getId().equals(employee.getId()))) {
            model.addAttribute("message", "Такой логин уже существует");
            return "employee/edit";
        }
        dbEmployee = employeeRepository.findById(employee.getId()).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "employee/edit";
        }

        if (new_password != null && new_password != "") {
            dbEmployee.setPassword(new BCryptPasswordEncoder().encode(new_password));
        }
        dbEmployee.setLogin(employee.getLogin());
        dbEmployee.setName(employee.getName());
        dbEmployee.setSurname(employee.getSurname());
        dbEmployee.setPatronymic(employee.getPatronymic());
        dbEmployee.setEmail(employee.getEmail());
        dbEmployee.setRole(employee.getRole());
        dbEmployee.setSeriesPassport(employee.getSeriesPassport());
        dbEmployee.setNumberPassport(employee.getNumberPassport());

        employeeRepository.save(dbEmployee);
        return "redirect:/employee";
    }
}

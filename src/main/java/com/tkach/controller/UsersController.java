package com.tkach.controller;

import com.tkach.model.*;
import com.tkach.services.*;
import com.tkach.util.UsersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final EmployeeService employeeService;
    private final UsersValidator usersValidator;

    @Autowired
    public UsersController(UsersService usersService,
                           EmployeeService employeeService,
                           UsersValidator usersValidator) {
        this.usersService = usersService;
        this.employeeService = employeeService;
        this.usersValidator = usersValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", usersService.findAll());
        return "users/index";
    }

    @GetMapping("/new")
    public String newUsers(@ModelAttribute("users") Users users) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("users") @Valid Users users,
                         BindingResult bindingResult) {
        usersValidator.validate(users, bindingResult);
        if (bindingResult.hasErrors())
            return "users/new";
        usersService.save(users);
        return "redirect:/users/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model,
                       @ModelAttribute("employee") Employee employee) {
        model.addAttribute("users", usersService.findOne(id));
        Employee usersIdEmpUse = usersService.getUsersIdEmpUse(id);
        if (usersIdEmpUse != null)
            model.addAttribute("idEmpUse", usersIdEmpUse);
        else
            model.addAttribute("employeeList", employeeService.findAll());;
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("users") @Valid Users users, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        usersValidator.validate(users, bindingResult);
        if (bindingResult.hasErrors())
            return "users/edit";
        usersService.update(id, users);
        return "redirect:/users/" + id + "/edit/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        usersService.delete(id);
        return "redirect:/users";
    }

    @PatchMapping("/{id}/releaseEmployee")
    public String releaseEmployee(@PathVariable("id") int id) {
        usersService.releaseEmployee(id);
        return "redirect:/users/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignEmployee")
    public String assignEmployee(@PathVariable("id") int id,
                                 @ModelAttribute("employee") Employee selectedEmployee) {
        usersService.assignEmployee(id, selectedEmployee);
        return "redirect:/users/" + id + "/edit/";
    }

}

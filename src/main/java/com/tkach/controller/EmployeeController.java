package com.tkach.controller;

import com.tkach.model.Employee;
import com.tkach.services.EmployeeService;
import com.tkach.util.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              EmployeeValidator employeeValidator) {
        this.employeeService = employeeService;
        this.employeeValidator = employeeValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("employeeList", employeeService.findAll());
        return "employee/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", employeeService.findOne(id));
        model.addAttribute("appointmentList", employeeService.getAppointmentByEmployeeId(id));
        model.addAttribute("usersList", employeeService.getUsersByEmployeeId(id));
        return "employee/show";
    }

    @GetMapping("/new")
    public String newEmployee(@ModelAttribute("employee") Employee employee) {
        return "employee/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("employee") @Valid Employee employee,
                         BindingResult bindingResult) {
        employeeValidator.validate(employee, bindingResult);
        if (bindingResult.hasErrors())
            return "employee/new";
        employeeService.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("employee", employeeService.findOne(id));
        return "employee/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        employeeValidator.validate(employee, bindingResult);
        if (bindingResult.hasErrors())
            return "employee/edit";
        employeeService.update(id, employee);
        return "redirect:/employee";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }
}
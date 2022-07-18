package com.tkach.controller;

import com.tkach.model.Department;
import com.tkach.services.DepartmentService;
import com.tkach.util.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentValidator departmentValidator;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentValidator departmentValidator) {
        this.departmentService = departmentService;
        this.departmentValidator = departmentValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("departmentList", departmentService.findAll());
        return "department/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("department", departmentService.findOne(id));
        model.addAttribute("appointmentList", departmentService.getAppointmentByDepartmentId(id));

        return "department/show";
    }

    @GetMapping("/new")
    public String newDepartment(@ModelAttribute("department") Department department) {
        return "department/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("department") @Valid Department department,
                         BindingResult bindingResult) {
        departmentValidator.validate(department, bindingResult);
        if (bindingResult.hasErrors())
            return "department/new";
        departmentService.save(department);
        return "redirect:/department";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("department", departmentService.findOne(id));
        return "department/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("department") @Valid Department department, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        departmentValidator.validate(department, bindingResult);
        if (bindingResult.hasErrors())
            return "department/edit";
        departmentService.update(id, department);
        return "redirect:/department";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        departmentService.delete(id);
        return "redirect:/department";
    }
}
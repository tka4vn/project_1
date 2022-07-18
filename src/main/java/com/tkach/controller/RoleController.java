package com.tkach.controller;

import com.tkach.model.Role;
import com.tkach.services.RoleService;
import com.tkach.util.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;
    private final RoleValidator roleValidator;

    @Autowired
    public RoleController(RoleService roleService,
                          RoleValidator roleValidator) {
        this.roleService = roleService;
        this.roleValidator = roleValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("roleList", roleService.findAll());
        return "role/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("role", roleService.findOne(id));
        model.addAttribute("ingressList", roleService.getIngressByRoleId(id));
        model.addAttribute("requestList", roleService.getRequestByRoleId(id));
        return "role/show";
    }

    @GetMapping("/new")
    public String newRole(@ModelAttribute("role") Role role) {
        return "role/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("role") @Valid Role role,
                         BindingResult bindingResult) {
        roleValidator.validate(role, bindingResult);
        if (bindingResult.hasErrors())
            return "role/new";
        roleService.save(role);
        return "redirect:/role";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("role", roleService.findOne(id));
        return "role/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("role") @Valid Role role, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        roleValidator.validate(role, bindingResult);
        if (bindingResult.hasErrors())
            return "role/edit";
        roleService.update(id, role);
        return "redirect:/role";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        roleService.delete(id);
        return "redirect:/role";
    }
}
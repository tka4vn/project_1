package com.tkach.controller;

import com.tkach.model.Ingress;
import com.tkach.model.Role;
import com.tkach.model.Services;
import com.tkach.model.Users;
import com.tkach.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/ingress")
public class IngressController {
    private final IngressService ingressService;
    private final UsersService usersService;
    private final ServicesService servicesService;
    private final RoleService roleService;

    @Autowired
    public IngressController(IngressService ingressService,
                             UsersService usersService,
                             ServicesService servicesService,
                             RoleService roleService) {
        this.ingressService = ingressService;
        this.usersService = usersService;
        this.servicesService = servicesService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "ingress_per_page", required = false) Integer ingressPerPage,
                        @RequestParam(value = "sort_by_dateIng", required = false) boolean sortByDateIng) {

        if (page == null || ingressPerPage == null)
            model.addAttribute("ingress", ingressService.findAll(sortByDateIng)); // выдача всех книг
        else
            model.addAttribute("ingress", ingressService.findWithPagination(page, ingressPerPage, sortByDateIng));

        return "ingress/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("users") Users users,
                       @ModelAttribute("services") Services services,
                       @ModelAttribute("role") Role role) {
        model.addAttribute("ingress", ingressService.findOne(id));

        Users ingressIdUseIng = ingressService.getIngressIdUseIng(id);
        if (ingressIdUseIng != null)
            model.addAttribute("idUseIng", ingressIdUseIng);
        else
            model.addAttribute("usersList", usersService.findAll());

        Services ingressIdSerIng = ingressService.getIngressIdSerIng(id);
        if (ingressIdSerIng != null)
            model.addAttribute("idSerIng", ingressIdSerIng);
        else
            model.addAttribute("servicesList", servicesService.findAll());

        Role ingressIdRolIng = ingressService.getIngressIdRolIng(id);
        if (ingressIdRolIng != null)
            model.addAttribute("idRolIng", ingressIdRolIng);
        else
            model.addAttribute("roleList", roleService.findAll());

        return "ingress/show";
    }

    @GetMapping("/new")
    public String newIngresst(@ModelAttribute("ingress") Ingress ingress) {
        return "ingress/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("ingress") @Valid Ingress ingress,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ingress/new";
        ingressService.save(ingress);
        return "redirect:/ingress/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model,
                       @ModelAttribute("users") Users users,
                       @ModelAttribute("services") Services services,
                       @ModelAttribute("role") Role role) {
        model.addAttribute("ingress", ingressService.findOne(id));

        Users ingressIdUseIng = ingressService.getIngressIdUseIng(id);
        if (ingressIdUseIng != null)
            model.addAttribute("idUseIng", ingressIdUseIng);
        else
            model.addAttribute("usersList", usersService.findAll());

        Services ingressIdSerIng = ingressService.getIngressIdSerIng(id);
        if (ingressIdSerIng != null)
            model.addAttribute("idSerIng", ingressIdSerIng);
        else
            model.addAttribute("servicesList", servicesService.findAll());

        Role ingressIdRolIng = ingressService.getIngressIdRolIng(id);
        if (ingressIdRolIng != null)
            model.addAttribute("idRolIng", ingressIdRolIng);
        else
            model.addAttribute("roleList", roleService.findAll());

        return "ingress/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("ingress") @Valid Ingress ingress, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "ingress/edit";

        ingressService.update(id, ingress);
        return "redirect:/ingress/" + id + "/edit/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        ingressService.delete(id);
        return "redirect:/ingress";
    }

    @PatchMapping("/{id}/releaseUsers")
    public String releaseUsers(@PathVariable("id") int id) {
        ingressService.releaseUsers(id);
        return "redirect:/ingress/" + id + "/edit/";
    }

    @PatchMapping("/{id}/releaseServices")
    public String releaseServices(@PathVariable("id") int id) {
        ingressService.releaseServices(id);
        return "redirect:/ingress/" + id + "/edit/";
    }

    @PatchMapping("/{id}/releaseRole")
    public String releaseRole(@PathVariable("id") int id) {
        ingressService.releaseRole(id);
        return "redirect:/ingress/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignUsers")
    public String assignUsers(@PathVariable("id") int id,
                         @ModelAttribute("users") Users selectedUsers) {
        ingressService.assignUsers(id, selectedUsers);
        return "redirect:/ingress/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignServices")
    public String assignServices(@PathVariable("id") int id,
                                 @ModelAttribute("services") Services selectedServices) {
        ingressService.assignServices(id, selectedServices);
        return "redirect:/ingress/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignRole")
    public String assignRole(@PathVariable("id") int id,
                                 @ModelAttribute("role") Role selectedRole) {
        ingressService.assignRole(id, selectedRole);
        return "redirect:/ingress/" + id + "/edit/";
    }

}

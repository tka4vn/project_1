package com.tkach.controller;

import com.tkach.model.Services;
import com.tkach.services.ServicesService;
import com.tkach.util.ServicesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/services")
public class ServicesController {

    private final ServicesService servicesService;
    private final ServicesValidator servicesValidator;


    @Autowired
    public ServicesController(ServicesService servicesService,
                              ServicesValidator servicesValidator) {
        this.servicesService = servicesService;
        this.servicesValidator = servicesValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("servicesList", servicesService.findAll());
        return "services/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("services", servicesService.findOne(id));
        model.addAttribute("ingressList", servicesService.getIngressByServicesId(id));
        model.addAttribute("requestList", servicesService.getRequestByServicesId(id));
        return "services/show";
    }

    @GetMapping("/new")
    public String newServices(@ModelAttribute("services") Services services) {
        return "services/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("services") @Valid Services services,
                         BindingResult bindingResult) {
        servicesValidator.validate(services, bindingResult);
        if (bindingResult.hasErrors())
            return "services/new";
        servicesService.save(services);
        return "redirect:/services";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("services", servicesService.findOne(id));
        return "services/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("services") @Valid Services services, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        servicesValidator.validate(services, bindingResult);
        if (bindingResult.hasErrors())
            return "services/edit";
        servicesService.update(id, services);
        return "redirect:/services";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        servicesService.delete(id);
        return "redirect:/services";
    }
}
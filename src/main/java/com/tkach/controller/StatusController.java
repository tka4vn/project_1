package com.tkach.controller;

import com.tkach.model.Status;
import com.tkach.services.StatusService;
import com.tkach.util.StatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;
    private final StatusValidator statusValidator;

    @Autowired
    public StatusController(StatusService statusService,
                            StatusValidator statusValidator) {
        this.statusService = statusService;
        this.statusValidator = statusValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("statusList", statusService.findAll());
        return "status/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("status", statusService.findOne(id));
        model.addAttribute("requestList", statusService.getRequestByStatusId(id));
        return "status/show";
    }

    @GetMapping("/new")
    public String newStatus(@ModelAttribute("status") Status status) {
        return "status/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("status") @Valid Status status,
                         BindingResult bindingResult) {
        statusValidator.validate(status, bindingResult);
        if (bindingResult.hasErrors())
            return "status/new";
        statusService.save(status);
        return "redirect:/status";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("status", statusService.findOne(id));
        return "status/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("status") @Valid Status status, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        statusValidator.validate(status, bindingResult);
        if (bindingResult.hasErrors())
            return "status/edit";
        statusService.update(id, status);
        return "redirect:/status";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        statusService.delete(id);
        return "redirect:/status";
    }
}
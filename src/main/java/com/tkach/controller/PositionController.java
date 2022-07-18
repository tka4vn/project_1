package com.tkach.controller;

import com.tkach.model.Position;
import com.tkach.services.PositionService;
import com.tkach.util.PositionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/position")
public class PositionController {

    private final PositionService positionService;
    private final PositionValidator positionValidator;

    @Autowired
    public PositionController(PositionService positionService, PositionValidator positionValidator) {
        this.positionService = positionService;
        this.positionValidator = positionValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("positionList", positionService.findAll());
        return "position/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("position", positionService.findOne(id));
        model.addAttribute("appointmentList", positionService.getAppointmentByPositionId(id));
        return "position/show";
    }

    @GetMapping("/new")
    public String newPosition(@ModelAttribute("position") Position position) {
        return "position/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("position") @Valid Position position,
                         BindingResult bindingResult) {
        positionValidator.validate(position, bindingResult);
        if (bindingResult.hasErrors())
            return "position/new";
        positionService.save(position);
        return "redirect:/position";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("position", positionService.findOne(id));
        return "position/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("position")
                         @Valid Position position,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        positionValidator.validate(position, bindingResult);
        if (bindingResult.hasErrors())
            return "position/edit";
        positionService.update(id, position);
        return "redirect:/position";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        positionService.delete(id);
        return "redirect:/position";
    }
}
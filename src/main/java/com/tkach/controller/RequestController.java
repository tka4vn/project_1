package com.tkach.controller;

import com.tkach.model.*;
import com.tkach.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;
    private final UsersService usersService;
    private final ServicesService servicesService;
    private final RoleService roleService;
    private final StatusService statusService;

    @Autowired
    public RequestController(RequestService requestService,
                             UsersService usersService,
                             ServicesService servicesService,
                             RoleService roleService,
                             StatusService statusService) {
        this.requestService = requestService;
        this.usersService = usersService;
        this.servicesService = servicesService;
        this.roleService = roleService;
        this.statusService = statusService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "request_per_page", required = false) Integer requestPerPage,
                        @RequestParam(value = "sort_by_dateReq", required = false) boolean sortByDateReq) {
        if (page == null || requestPerPage == null)
            model.addAttribute("request", requestService.findAll(sortByDateReq)); // выдача всех книг
        else
            model.addAttribute("request", requestService.findWithPagination(page, requestPerPage, sortByDateReq));
        return "request/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("users") Users users,
                       @ModelAttribute("services") Services services,
                       @ModelAttribute("role") Role role,
                       @ModelAttribute("status") Status status) {
        model.addAttribute("request", requestService.findOne(id));

        Users requestIdUseReq = requestService.getRequestIdUseReq(id);
        if (requestIdUseReq != null)
            model.addAttribute("idUseReq", requestIdUseReq);
        else
            model.addAttribute("usersList", usersService.findAll());

        Services requestIdSerReq = requestService.getRequestIdSerReq(id);
        if (requestIdSerReq != null)
            model.addAttribute("idSerReq", requestIdSerReq);
        else
            model.addAttribute("servicesList", servicesService.findAll());

        Role requestIdRolReq = requestService.getRequestIdRolReq(id);
        if (requestIdRolReq != null)
            model.addAttribute("idRolReq", requestIdRolReq);
        else
            model.addAttribute("roleList", roleService.findAll());

        Status requestIdStaReq = requestService.getRequestIdStaReq(id);
        if (requestIdStaReq != null)
            model.addAttribute("idStaReq", requestIdStaReq);
        else
            model.addAttribute("statusList", statusService.findAll());

        return "request/show";
    }

    @GetMapping("/new")
    public String newRequest(@ModelAttribute("request") Request request) {
        return "request/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("request") @Valid Request request,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "request/new";
        requestService.save(request);
        return "redirect:/request/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model,
                       @ModelAttribute("users") Users users,
                       @ModelAttribute("services") Services services,
                       @ModelAttribute("role") Role role,
                       @ModelAttribute("status") Status status) {
        model.addAttribute("request", requestService.findOne(id));

        Users requestIdUseReq = requestService.getRequestIdUseReq(id);
        if (requestIdUseReq != null)
            model.addAttribute("idUseReq", requestIdUseReq);
        else
            model.addAttribute("usersList", usersService.findAll());

        Services requestIdSerReq = requestService.getRequestIdSerReq(id);
        if (requestIdSerReq != null)
            model.addAttribute("idSerReq", requestIdSerReq);
        else
            model.addAttribute("servicesList", servicesService.findAll());

        Role requestIdRolReq = requestService.getRequestIdRolReq(id);
        if (requestIdRolReq != null)
            model.addAttribute("idRolReq", requestIdRolReq);
        else
            model.addAttribute("roleList", roleService.findAll());

        Status requestIdStaReq = requestService.getRequestIdStaReq(id);
        if (requestIdStaReq != null)
            model.addAttribute("idStaReq", requestIdStaReq);
        else
            model.addAttribute("statusList", statusService.findAll());

        return "request/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("request") @Valid Request request, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "request/edit";
        requestService.update(id, request);
        return "redirect:/request/" + id + "/edit/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        requestService.delete(id);
        return "redirect:/request";
    }

    @PatchMapping("/{id}/releaseUsers")
    public String releaseUsers(@PathVariable("id") int id) {
        requestService.releaseUsers(id);
        return "redirect:/request/" + id + "/edit/";
    }

    @PatchMapping("/{id}/releaseServices")
    public String releaseServices(@PathVariable("id") int id) {
        requestService.releaseServices(id);
        return "redirect:/request/" + id + "/edit/";
    }

    @PatchMapping("/{id}/releaseRole")
    public String releaseRole(@PathVariable("id") int id) {
        requestService.releaseRole(id);
        return "redirect:/request/" + id + "/edit/";
    }

    @PatchMapping("/{id}/releaseStatus")
    public String releaseStatus(@PathVariable("id") int id) {
        requestService.releaseStatus(id);
        return "redirect:/request/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignUsers")
    public String assignUsers(@PathVariable("id") int id,
                         @ModelAttribute("users") Users selectedUsers) {
        requestService.assignUsers(id, selectedUsers);
        return "redirect:/request/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignServices")
    public String assignServices(@PathVariable("id") int id,
                                 @ModelAttribute("services") Services selectedServices) {
        requestService.assignServices(id, selectedServices);
        return "redirect:/request/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignRole")
    public String assignRole(@PathVariable("id") int id,
                                 @ModelAttribute("role") Role selectedRole) {
        requestService.assignRole(id, selectedRole);
        return "redirect:/request/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignStatus")
    public String assignStatus(@PathVariable("id") int id,
                                 @ModelAttribute("status") Status selectedStatus) {
        requestService.assignStatus(id, selectedStatus);
        return "redirect:/request/" + id + "/edit/";
    }

}

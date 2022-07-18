package com.tkach.controller;

import com.tkach.model.Appointment;
import com.tkach.model.Department;
import com.tkach.model.Employee;
import com.tkach.model.Position;
import com.tkach.services.AppointmentService;
import com.tkach.services.DepartmentService;
import com.tkach.services.EmployeeService;
import com.tkach.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final DepartmentService departmentService;
    private final PositionService positionService;
    private final EmployeeService employeeService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService,
                                 DepartmentService departmentService,
                                 PositionService positionService,
                                 EmployeeService employeeService) {
        this.appointmentService = appointmentService;
        this.departmentService = departmentService;
        this.positionService = positionService;
        this.employeeService = employeeService;
    }


    @GetMapping()
    public String index(Model model,
                        @ModelAttribute("department") Department department,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "appointment_per_page", required = false) Integer appointmentPerPage,
                        @RequestParam(value = "sort_by_dateApp", required = false) boolean sortByDateApp) {

        if (page == null || appointmentPerPage == null)
            model.addAttribute("appointment", appointmentService.findAll(sortByDateApp));
        else
            model.addAttribute("appointment", appointmentService.findWithPagination(page, appointmentPerPage, sortByDateApp));

        return "appointment/index";
    }

    @GetMapping("/new")
    public String newAppointment(@ModelAttribute("appointment") Appointment appointment) {
        return "appointment/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("appointment") @Valid Appointment appointment,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "appointment/new";
        appointmentService.save(appointment);
        return "redirect:/appointment/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model,
                       @ModelAttribute("department") Department department,
                       @ModelAttribute("position") Position position,
                       @ModelAttribute("employee") Employee employee) {
        model.addAttribute("appointment", appointmentService.findOne(id));

        Department appointmentIdDepApp = appointmentService.getAppointmentIdDepApp(id);
        if (appointmentIdDepApp != null)
            model.addAttribute("idDepApp", appointmentIdDepApp);
        else
            model.addAttribute("departmentList", departmentService.findAll());

        Position appointmentIdPosApp = appointmentService.getAppointmentIdPosApp(id);
        if (appointmentIdPosApp != null)
            model.addAttribute("idPosApp", appointmentIdPosApp);
        else
            model.addAttribute("positionList", positionService.findAll());

        Employee appointmentIdEmpApp = appointmentService.getAppointmentIdEmpApp(id);
        if (appointmentIdEmpApp != null)
            model.addAttribute("idEmpApp", appointmentIdEmpApp);
        else
            model.addAttribute("employeeList", employeeService.findAll());;

        return "appointment/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "appointment/edit";

        appointmentService.update(id, appointment);
        return "redirect:/appointment/" + id + "/edit/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        appointmentService.delete(id);
        return "redirect:/appointment";
    }

    @PatchMapping("/{id}/releaseDepartment")
    public String releaseDepartment(@PathVariable("id") int id) {
        appointmentService.releaseDepartment(id);
        return "redirect:/appointment/" + id + "/edit/";
    }

    @PatchMapping("/{id}/releasePosition")
    public String releasePosition(@PathVariable("id") int id) {
        appointmentService.releasePosition(id);
        return "redirect:/appointment/" + id + "/edit/";
    }

    @PatchMapping("/{id}/releaseEmployee")
    public String releaseEmployee(@PathVariable("id") int id) {
        appointmentService.releaseEmployee(id);
        return "redirect:/appointment/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignDepartment")
    public String assignDepartment(@PathVariable("id") int id,
                         @ModelAttribute("department") Department selectedDepartment) {
        appointmentService.assignDepartment(id, selectedDepartment);
        return "redirect:/appointment/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignPosition")
    public String assignPosition(@PathVariable("id") int id,
                                 @ModelAttribute("position") Position selectedPosition) {
        appointmentService.assignPosition(id, selectedPosition);
        return "redirect:/appointment/" + id + "/edit/";
    }

    @PatchMapping("/{id}/assignEmployee")
    public String assignEmployee(@PathVariable("id") int id,
                                 @ModelAttribute("employee") Employee selectedEmployee) {
        appointmentService.assignEmployee(id, selectedEmployee);
        return "redirect:/appointment/" + id + "/edit/";
    }

}

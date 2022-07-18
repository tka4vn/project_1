package com.tkach.util;

import com.tkach.model.Employee;
import com.tkach.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmployeeValidator implements Validator {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeValidator(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;
        if (employeeService.getEmployeeByFullName(employee.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Такой сотрудник уже существует");
    }
}

package com.tkach.util;

import com.tkach.model.Department;
import com.tkach.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DepartmentValidator implements Validator {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Department.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Department department = (Department) o;
        if (departmentService.getDepartmentByName(department.getName()).isPresent())
            errors.rejectValue("name", "", "Такой департамент уже существует");
    }
}

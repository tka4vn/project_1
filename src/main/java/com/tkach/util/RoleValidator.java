package com.tkach.util;

import com.tkach.model.Role;
import com.tkach.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RoleValidator implements Validator {

    private final RoleService roleService;

    @Autowired
    public RoleValidator(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Role.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Role role = (Role) o;
        if (roleService.getRoleByName(role.getName()).isPresent())
            errors.rejectValue("name", "", "Такая роль уже существует");
    }
}

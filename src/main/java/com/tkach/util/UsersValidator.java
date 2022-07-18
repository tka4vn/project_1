package com.tkach.util;

import com.tkach.model.Users;
import com.tkach.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsersValidator implements Validator {

    private final UsersService usersService;

    @Autowired
    public UsersValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users users = (Users) o;
        if (usersService.getUsersByName(users.getName()).isPresent())
            errors.rejectValue("name", "", "Такой аккаунт уже существует");
    }
}

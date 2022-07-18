package com.tkach.util;

import com.tkach.model.Services;
import com.tkach.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ServicesValidator implements Validator {

    private final ServicesService servicesService;

    @Autowired
    public ServicesValidator(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Services.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Services services = (Services) o;
        if (servicesService.getServicesByName(services.getName()).isPresent())
            errors.rejectValue("name", "", "Такой сервис уже существует");
    }
}

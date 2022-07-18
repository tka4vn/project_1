package com.tkach.util;

import com.tkach.model.Status;
import com.tkach.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StatusValidator implements Validator {

    private final StatusService statusService;

    @Autowired
    public StatusValidator(StatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Status.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Status status = (Status) o;
        if (statusService.getStatusByName(status.getName()).isPresent())
            errors.rejectValue("name", "", "Такой статус уже существует");
    }
}

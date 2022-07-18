package com.tkach.util;

import com.tkach.model.Position;
import com.tkach.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PositionValidator implements Validator {

    private final PositionService positionService;

    @Autowired
    public PositionValidator(PositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Position.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Position position = (Position) o;
        if (positionService.getPositionByName(position.getName()).isPresent())
            errors.rejectValue("name", "", "Такая должность уже существует");
    }
}

package ru.practicum.ewm.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDateTime;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class StartBeforeEndDateConstraintValidator implements ConstraintValidator<StartBeforeEndDateConstraint, Object[]> {

    @Override
    public boolean isValid(Object[] values, ConstraintValidatorContext context) {
        if (values[0] == null || values[1] == null) {
            return true;
        }

        if (!(values[0] instanceof LocalDateTime) || !(values[1] instanceof LocalDateTime)) {
            throw new IllegalArgumentException(
                    "Illegal method signature, expected two parameters of type LocalDateTime.");
        }

        LocalDateTime start = (LocalDateTime) values[0];
        LocalDateTime end = (LocalDateTime) values[1];

        return start.isBefore(end);
    }
}

package ru.practicum.ewm.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateAfterValueHourFutureConstraintValidator implements ConstraintValidator<DateAfterValueHourFutureConstraint, LocalDateTime> {
    private Integer hour;

    @Override
    public void initialize(DateAfterValueHourFutureConstraint annotation) {
        hour = Integer.valueOf(annotation.value());
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.isAfter(LocalDateTime.now().plusHours(hour));
    }
}

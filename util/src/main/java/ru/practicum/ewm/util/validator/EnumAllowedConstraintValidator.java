package ru.practicum.ewm.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumAllowedConstraintValidator implements ConstraintValidator<EnumAllowedConstraint, Enum<?>> {
    private Set<String> allowedValues;
    private Set<String> enumValues;

    @Override
    public void initialize(EnumAllowedConstraint constraint) {
        this.allowedValues = Arrays.stream(constraint.allowed()).collect(Collectors.toSet());
        this.enumValues = Stream.of(constraint.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value == null || enumValues.contains(value.toString()) && allowedValues.contains(value.toString());
    }
}

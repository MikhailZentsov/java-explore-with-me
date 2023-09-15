package ru.practicum.ewm.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Constraint(validatedBy = EnumAllowedConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumAllowedConstraint {

    String[] allowed();

    Class<? extends Enum<?>> enumClass();

    String message() default "must be any of {allowed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

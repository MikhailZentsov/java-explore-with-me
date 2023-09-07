package ru.practicum.ewm.util.aspect.log;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface ToLog {
}

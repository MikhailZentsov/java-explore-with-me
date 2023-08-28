package ru.practicum.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("@within(ru.practicum.aspect.log.ToLog)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        log.trace("Метод " + methodName +
                " с параметрами " + Arrays.asList(arguments) +
                " будет выполнен");

        Object returnedByMethod = joinPoint.proceed();

        log.trace("Метод " + methodName +
                " выполнен и вернул " + returnedByMethod);

        return returnedByMethod;
    }
}

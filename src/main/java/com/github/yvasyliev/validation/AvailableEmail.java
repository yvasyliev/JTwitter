package com.github.yvasyliev.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AvailableEmailValidator.class)
public @interface AvailableEmail {
    String message() default "This email is already in use.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

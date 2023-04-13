package com.github.yvasyliev.validation;

import com.github.yvasyliev.repository.UserRepository;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AvailableUsername.Validator.class)
public @interface AvailableUsername {
    String message() default "This username is already in use.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<AvailableUsername, String> {
        @Autowired
        private UserRepository userRepository;

        @Override
        public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
            return !userRepository.existsByUsername(username);
        }
    }
}

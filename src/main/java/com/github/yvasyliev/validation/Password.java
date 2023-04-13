package com.github.yvasyliev.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Password.Validator.class)
public @interface Password {
    String message() default "Invalid Password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Password, String> {
        @Autowired
        private PasswordValidator passwordValidator;

        @Override
        public boolean isValid(String password, ConstraintValidatorContext context) {
            var result = passwordValidator.validate(new PasswordData(password));
            var valid = result.isValid();

            if (!valid) {
                context.disableDefaultConstraintViolation();
                passwordValidator.getMessages(result)
                        .forEach(message -> context
                                .buildConstraintViolationWithTemplate(message)
                                .addConstraintViolation()
                        );
            }

            return valid;
        }
    }
}
package com.github.yvasyliev.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatch.Validator.class)
public @interface PasswordsMatch {
    String message() default "Passwords do not match.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String password();
    String confirmPassword();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PasswordsMatch[] value();
    }

    class Validator implements ConstraintValidator<PasswordsMatch, Object> {
        private String password;
        private String confirmPassword;
        private String message;

        public void initialize(PasswordsMatch constraintAnnotation) {
            this.password = constraintAnnotation.password();
            this.confirmPassword = constraintAnnotation.confirmPassword();
            this.message = constraintAnnotation.message();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            var fieldValue = new BeanWrapperImpl(value).getPropertyValue(password);
            var fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

            var isValid = fieldValue != null && fieldValue.equals(fieldMatchValue);

            if (!isValid) {
                context
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(confirmPassword)
                        .addConstraintViolation();
            }

            return isValid;
        }
    }
}

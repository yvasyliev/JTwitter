package com.github.yvasyliev.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
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

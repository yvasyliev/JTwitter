package com.github.yvasyliev.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
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

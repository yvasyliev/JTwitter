package com.github.yvasyliev.validation;

import com.github.yvasyliev.model.entity.token.TokenType;
import com.github.yvasyliev.repository.TokenRepository;
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
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEmailToken.Validator.class)
public @interface ValidEmailToken {
    String message() default "Invalid token.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ValidEmailToken, String> {
        @Autowired
        private TokenRepository tokenRepository;

        @Override
        public boolean isValid(String tokenId, ConstraintValidatorContext validatorContext) {
            var optionalToken = tokenRepository.findByIdAndTokenType(tokenId, TokenType.EMAIL);

            if (optionalToken.isEmpty()) {
                validatorContext
                        .buildConstraintViolationWithTemplate("Unknown token.")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                return false;
            }

            var token = optionalToken.get();

            if (token.isExpired()) {
                validatorContext
                        .buildConstraintViolationWithTemplate("Token expired.")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                return false;
            }

            if (token.getRevoked()) {
                validatorContext
                        .buildConstraintViolationWithTemplate("Token revoked.")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
                return false;
            }

            return true;
        }
    }
}

package com.github.yvasyliev.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SupportedImageFormat.Validator.class)
public @interface SupportedImageFormat {
    String message() default "Unsupported image format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<SupportedImageFormat, MultipartFile> {
        @Value("#{{'image/png', 'image/jpeg', 'image/jpg'}}")
        private Set<String> supportedTypes;

        @Override
        public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
            return supportedTypes.contains(image.getContentType());
        }
    }
}

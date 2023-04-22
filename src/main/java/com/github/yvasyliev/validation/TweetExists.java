package com.github.yvasyliev.validation;

import com.github.yvasyliev.repository.TweetRepository;
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
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TweetExists {
    String message() default "Tweet doesn't exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<TweetExists, Long> {
        @Autowired
        private TweetRepository tweetRepository;

        @Override
        public boolean isValid(Long tweetId, ConstraintValidatorContext constraintValidatorContext) {
            return tweetRepository.existsById(tweetId);
        }
    }
}

package com.github.yvasyliev.validation;

import com.github.yvasyliev.repository.TweetRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TweetExists {
    String message() default "Parent tweet doesn't exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<TweetExists, Long> {
        @Autowired
        private TweetRepository tweetRepository;

        @Override
        public boolean isValid(Long parentTweetId, ConstraintValidatorContext constraintValidatorContext) {
            return parentTweetId == null || tweetRepository.existsById(parentTweetId);
        }
    }
}

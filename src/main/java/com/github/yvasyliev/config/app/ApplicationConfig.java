package com.github.yvasyliev.config.app;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.passay.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.security.Key;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ApplicationConfig {
    @Value("${jwt.secret_key}")
    private String jwtSecretKey;

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator(
                lengthRule(),
                uppercaseRule(),
                lowercaseRule(),
                digitRule(),
                specialRule(),
                whitespaceRule(),
                alphabeticalSequenceRule(),
                numericalSequenceRule()
        );
    }

    /**
     * Length between 8 and 16 characters.
     *
     * @return password rule.
     */
    @Bean
    public Rule lengthRule() {
        return new LengthRule(8, 16);
    }

    /**
     * At least one upper-case character
     *
     * @return password rule.
     */
    @Bean
    public Rule uppercaseRule() {
        return new CharacterRule(EnglishCharacterData.UpperCase, 1);
    }

    /**
     * At least one lower-case character
     *
     * @return password rule.
     */
    @Bean
    public Rule lowercaseRule() {
        return new CharacterRule(EnglishCharacterData.LowerCase, 1);
    }

    /**
     * At least one digit character.
     *
     * @return password rule.
     */
    @Bean
    public Rule digitRule() {
        return new CharacterRule(EnglishCharacterData.Digit, 1);
    }

    /**
     * At least one symbol (special character).
     *
     * @return password rule.
     */
    @Bean
    public Rule specialRule() {
        return new CharacterRule(EnglishCharacterData.Special, 1);
    }

    /**
     * No whitespace.
     *
     * @return password rule.
     */
    @Bean
    public Rule whitespaceRule() {
        return new WhitespaceRule();
    }

    /**
     * Rejects passwords that contain a sequence of >= 5 characters alphabetical  (e.g. abcdef)
     *
     * @return password rule.
     */
    @Bean
    public Rule alphabeticalSequenceRule() {
        return new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false);
    }

    /**
     * Rejects passwords that contain a sequence of >= 5 characters numerical   (e.g. 12345).
     *
     * @return password rule.
     */
    @Bean
    public Rule numericalSequenceRule() {
        return new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false);
    }

    @Bean
    public Executor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.initialize();
        return executor;
    }

    @Bean
    public Key signingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder().setSigningKey(signingKey()).build();
    }
}

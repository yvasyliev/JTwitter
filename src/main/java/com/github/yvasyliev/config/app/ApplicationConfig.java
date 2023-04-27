package com.github.yvasyliev.config.app;

import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.uitls.RequestUtils;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Key;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    @Bean
    public BiFunction<HttpServletRequest, Token, String> emailConfirmationTextFactory() {
        return (request, token) -> "Please follow the link to confirm email: " + UriComponentsBuilder
                .fromHttpUrl(RequestUtils.getHost(request))
                .pathSegment("api", "v1", "auth", "confirm")
                .queryParam("token", token.getId())
                .toUriString();
    }

    @Bean
    public Function<HttpServletRequest, String> passwordChangedTextFactory() {
        return request ->
                "Your password has been changed. If it wasn't you, please reset your password now: %s"
                        .formatted(
                                UriComponentsBuilder
                                        .fromHttpUrl(RequestUtils.getHost(request))
                                        .pathSegment("resetPassword")
                                        .toUriString()
                        );
    }
}

package com.github.yvasyliev.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class UsernameValidator implements Predicate<String> {
    private static final String REGEX = "[a-zA-Z\\d]{6,}";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Value("#{T(java.util.regex.Pattern).compile('[a-zA-Z\\d]{6,}')}")
    private Pattern pattern;

    @Override
    public boolean test(String username) {
        return pattern.matcher(username).matches();
    }
}

package com.github.yvasyliev.service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        // TODO: 10.03.2022 implement
        return true;
    }
}

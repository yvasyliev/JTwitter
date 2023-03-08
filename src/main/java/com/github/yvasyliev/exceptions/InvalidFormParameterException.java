package com.github.yvasyliev.exceptions;

import java.util.Map;

public class InvalidFormParameterException extends IllegalArgumentException {
    private final Map<String, String> body;

    public InvalidFormParameterException(String name, String value, String message) {
        super(message);
        this.body = Map.of(
                "name", name,
                "value", value,
                "message", message
        );
    }

    public Map<String, String> getBody() {
        return body;
    }
}

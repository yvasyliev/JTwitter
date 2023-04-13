package com.github.yvasyliev.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record SignInForm(@NotEmpty String usernameOrEmail, @NotEmpty String password) {
}

package com.github.yvasyliev.model.dto;

import com.github.yvasyliev.validation.Password;
import com.github.yvasyliev.validation.PasswordsMatch;
import jakarta.validation.constraints.NotBlank;

@PasswordsMatch.List(@PasswordsMatch(password = "newPassword", confirmPassword = "confirmNewPassword"))
public record UpdatePasswordForm(
        @NotBlank
        String currentPassword,

        @NotBlank
        @Password
        String newPassword,

        @NotBlank
        String confirmNewPassword
) {
}

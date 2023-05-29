package com.github.yvasyliev.model.dto;

import com.github.yvasyliev.validation.AvailableEmail;
import com.github.yvasyliev.validation.AvailableUsername;
import com.github.yvasyliev.validation.Password;
import com.github.yvasyliev.validation.PasswordsMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordsMatch.List(@PasswordsMatch(password = "password", confirmPassword = "confirmPassword"))
public record SignUpForm(
        @NotEmpty
        @Size(min = 6, max = 32, message = "Username must be between six and 32 characters long.")
        @Pattern(regexp = ".*[a-zA-Z]+.*", message = "Username must contain at least one a-z or A-Z letter.")
        @Pattern(regexp = "[a-zA-Z\\d_]+", message = "Username can contain a-z and A-Z letters, numbers and '_'.")
        @AvailableUsername
        String username,

        @Email(message = "Invalid email address.")
        @AvailableEmail
        String email,

        @NotEmpty
        @Size(min = 1, max = 32)
        String firstName,

        @NotEmpty
        @Size(min = 1, max = 32)
        String lastName,

        @NotEmpty
        @Password
        String password,

        @NotEmpty
        String confirmPassword
) {
}

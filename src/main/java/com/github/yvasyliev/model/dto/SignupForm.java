package com.github.yvasyliev.model.dto;

import com.github.yvasyliev.validation.AvailableEmail;
import com.github.yvasyliev.validation.AvailableUsername;
import com.github.yvasyliev.validation.PasswordsMatch;
import com.github.yvasyliev.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordsMatch.List(@PasswordsMatch(password = "password", confirmPassword = "confirmPassword"))
public class SignupForm {
    @NotEmpty
    @Size(min = 6, max = 32, message = "Username must be between six and 32 characters long.")
    @Pattern(regexp = ".*[a-zA-Z]+.*", message = "Username must contain at least one a-z or A-Z letter.")
    @Pattern(regexp = "[a-zA-Z\\d_]+", message = "Username can contain a-z and A-Z letters, numbers and '_'.")
    @AvailableUsername
    private String username;

    @Email(message = "Invalid email address.")
    @AvailableEmail
    private String email;

    @NotEmpty
    @Size(min = 1, max = 32)
    private String firstName;

    @NotEmpty
    @Size(min = 1, max = 32)
    private String lastName;

    @NotEmpty
    @Password
    private String password;

    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

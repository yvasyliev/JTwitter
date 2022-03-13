package com.github.yvasyliev.service;

import com.github.yvasyliev.model.entity.ConfirmationToken;
import com.github.yvasyliev.model.entity.user.Role;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.model.dto.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class RegistrationService {
    private static final String EMAIL_NOT_VALID_MSG = "Email '%s' is not valid.";

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationForm registrationForm) {
        boolean isValidEmail = emailValidator.test(registrationForm.getEmail());

        if (!isValidEmail) {
            throw new IllegalArgumentException(String.format(
                    EMAIL_NOT_VALID_MSG,
                    registrationForm.getEmail()
            ));
        }

        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setEmail(registrationForm.getEmail());
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setPassword(registrationForm.getPassword());
        user.setRole(Role.UNCONFIRMED_USER);

        return userDetailsService.signUp(user);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token was not found."));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email is already confirmed.");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();

        if (expiresAt.isBefore(now)) {
            throw new IllegalStateException("Token is expired.");
        }

        confirmationToken.setConfirmedAt(now);

        userDetailsService.enableUser(confirmationToken.getUser());

        return "confirmed";
    }
}

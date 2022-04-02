package com.github.yvasyliev.service;

import com.github.yvasyliev.model.dto.RegistrationForm;
import com.github.yvasyliev.model.entity.ConfirmationToken;
import com.github.yvasyliev.model.entity.user.Role;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private static final String USER_ALREADY_EXISTS_MSG = "User '%s' is already exists.";
    private static final String EMAIL_NOT_VALID_MSG = "Email '%s' is not valid.";

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    public User register(RegistrationForm registrationForm) {
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

        return register(user);
    }

    @Transactional
    public User register(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(existingUser -> {
            throw new IllegalStateException(String.format(
                    USER_ALREADY_EXISTS_MSG,
                    existingUser.getUsername()
            ));
        });


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Transactional
    public void confirm(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.confirm(token);
        confirmationToken.getUser().setRole(Role.USER);
    }
}

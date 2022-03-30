package com.github.yvasyliev.service;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Transactional
    public ConfirmationToken register(User user) {
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();

        if (userExists) {
            throw new IllegalStateException(String.format(USER_ALREADY_EXISTS_MSG, user.getUsername()));
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return confirmationTokenService.createConfirmationToken(user);
    }

    @Transactional
    public void confirmUser(User User) {
        User.setRole(Role.USER);
    }
}

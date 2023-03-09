package com.github.yvasyliev.service.auth;

import com.github.yvasyliev.model.dto.SignupForm;
import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.user.Role;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public Token signup(SignupForm signupForm) {
        var user = new User();
        user.setUsername(signupForm.getUsername());
        user.setEmail(signupForm.getEmail());
        user.setFirstName(signupForm.getFirstName());
        user.setLastName(signupForm.getLastName());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        user.setRole(Role.USER);
        return tokenService.createJwtToken(userRepository.save(user));
    }

    @Transactional
    public User confirm(String token) {
        var user = tokenService.getById(token).getUser();
        user.setRole(Role.CONFIRMED_USER);
        return userRepository.save(user);
    }
}

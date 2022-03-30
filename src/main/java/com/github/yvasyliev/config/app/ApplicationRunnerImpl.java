package com.github.yvasyliev.config.app;

import com.github.yvasyliev.model.entity.user.Role;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        userRepository.save(buildUser(
                "admin",
                "admin",
                "john.doe@gmail.com",
                "John",
                "Doe",
                Role.ADMIN
        ));

        userRepository.save(buildUser(
                "user",
                "user",
                "brad.pitt@gmai.com",
                "Brad",
                "Pitt",
                Role.USER
        ));
    }

    private User buildUser(String username, String password, String email, String firstName, String lastName, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}

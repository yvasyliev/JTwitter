package com.github.yvasyliev.config.app;

import com.github.yvasyliev.model.entity.Tweet;
import com.github.yvasyliev.model.entity.user.Role;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.TweetRepository;
import com.github.yvasyliev.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TweetRepository tweetRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        User johnDoe = userRepository.save(buildUser(
                "admin",
                "admin",
                "john.doe@gmail.com",
                "John",
                "Doe",
                Role.ADMIN
        ));

        tweetRepository.save(buildTweet(johnDoe, "Hello World!"));

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

    private Tweet buildTweet(User user, String text) {
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setText(text);
        return tweet;
    }
}

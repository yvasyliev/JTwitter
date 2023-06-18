package com.github.yvasyliev.service;

import com.github.yvasyliev.model.dto.SignUpForm;
import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.user.Role;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Value("images/user/photo")
    private String userPhotoDir;

    @Transactional
    public Token signUp(SignUpForm signupForm) {
        var user = new User();
        user.setUsername(signupForm.username());
        user.setPassword(passwordEncoder.encode(signupForm.password()));
        user.setEmail(signupForm.email());
        user.setFirstName(signupForm.firstName());
        user.setLastName(signupForm.lastName());
        return tokenService.createJwtToken(userRepository.save(user));
    }

    @Transactional
    public User confirmEmail(String tokenId) {
        var user = tokenService.revoke(tokenId).getUser();
        user.setRole(Role.CONFIRMED_USER);
        return userRepository.save(user);
    }

    public Token signOut(String jwt) {
        return tokenService.revoke(jwt);
    }

    @Transactional
    public User updateEmail(String email, User user) {
        user.setEmail(email);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User updatePassword(String password, User user) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Transactional
    public void setUserPhoto(MultipartFile photo, User user) throws IOException {
        var filename = generateFilename(photo);
        user.setPhoto(filename);
        userRepository.save(user);
        photo.transferTo(Paths.get(userPhotoDir, filename));
    }

    @Transactional
    public User updateFirstName(String firstName, User user) {
        user.setFirstName(firstName);
        return userRepository.save(user);
    }

    @Transactional
    public User updateLastName(String lastName, User user) {
        user.setLastName(lastName);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private String generateFilename(MultipartFile file) {
        var contentType = Objects.requireNonNull(file.getContentType());
        var extension = contentType.substring(contentType.indexOf('/') + 1);
        return "%s.%s".formatted(
                UUID.randomUUID().toString(),
                extension
        );
    }
}

package com.github.yvasyliev.service;

import com.github.yvasyliev.model.entity.ConfirmationToken;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    public ConfirmationToken createConfirmationToken(User user) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setUser(user);

        confirmationTokenRepository.save(confirmationToken);

        return confirmationToken;
    }

    @Transactional
    public ConfirmationToken confirm(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token)
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

        return confirmationToken;
    }
}

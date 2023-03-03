package com.github.yvasyliev.service;

import com.github.yvasyliev.model.entity.ConfirmationToken;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.ConfirmationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return confirmationTokenRepository.save(confirmationToken);
    }

    @Transactional
    public ConfirmationToken confirm(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("CONFIRMATION_TOKENS was not found by token=" + token));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("ConfirmationToken is already confirmed: " + confirmationToken);
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();

        if (expiresAt.isBefore(now)) {
            throw new IllegalStateException("ConfirmationToken is expired: " + confirmationToken);
        }

        confirmationToken.setConfirmedAt(now);

        return confirmationToken;
    }
}

package com.github.yvasyliev.service.auth;

import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public Optional<Token> findById(String id) {
        return tokenRepository.findById(id);
    }

    public boolean isValid(String token, String username) {
        return tokenRepository.existsByIdAndUser_UsernameAndExpiresAtGreaterThanEqualAndRevokedFalse(
                token,
                username,
                LocalDateTime.now()
        );
    }

    @Transactional
    public Token createToken(User user) {
        var token = new Token();
        token.setId(UUID.randomUUID().toString());
        token.setUser(user);
        return tokenRepository.save(token);
    }
}

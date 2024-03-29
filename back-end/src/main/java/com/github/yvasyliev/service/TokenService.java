package com.github.yvasyliev.service;

import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.token.TokenType;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtService jwtService;

    public boolean isJWTValid(String token, String username) {
        return tokenRepository.isJWTValid(
                token,
                username
        );
    }

    @Transactional
    public Token createEmailToken(User user) {
        var token = new Token();
        token.setId(UUID.randomUUID().toString());
        token.setUser(user);
        token.setTokenType(TokenType.EMAIL);
        return tokenRepository.save(token);
    }

    @Transactional
    public Token createJwtToken(User user) {
        var token = new Token();
        token.setId(jwtService.createJwt(user));
        token.setUser(user);
        token.setTokenType(TokenType.JWT);
        return tokenRepository.save(token);
    }

    public Token getById(String tokenId) {
        return tokenRepository.findById(tokenId).orElseThrow();
    }

    @Transactional
    public Token revoke(String tokenId) {
        return revoke(getById(tokenId));
    }

    @Transactional
    public Token revoke(Token token) {
        token.setRevoked(true);
        return tokenRepository.save(token);
    }

    @Transactional
    public void revokeEmailToken(User user) {
        tokenRepository.revokeActiveEmailToken(user.getId());
    }
}

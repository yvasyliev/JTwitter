package com.github.yvasyliev.repository;

import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.token.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByIdAndTokenType(String id, TokenType tokenType);
    boolean existsByIdAndUser_UsernameAndExpiresAtGreaterThanEqualAndRevokedFalse(String id, String username, LocalDateTime expiresAt);
}

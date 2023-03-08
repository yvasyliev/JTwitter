package com.github.yvasyliev.repository;

import com.github.yvasyliev.model.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsByIdAndUser_UsernameAndExpiresAtGreaterThanEqualAndRevokedFalse(String id, String username, LocalDateTime expiresAt);
}

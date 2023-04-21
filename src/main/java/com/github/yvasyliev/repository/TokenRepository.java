package com.github.yvasyliev.repository;

import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.token.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    @Modifying
    @Query("""
            update
                Token t
            set
                t.revoked = true
            where
                t.user.id = :userId
                and t.expiresAt >= CURRENT_TIMESTAMP
                and t.tokenType = 'EMAIL'
                and not(t.revoked)""")
    int revokeActiveEmailToken(@Param("userId") Long userId);

    Optional<Token> findByIdAndTokenType(String id, TokenType tokenType);

    @Query("""
            select
                true
            from
                Token t
            where
                t.id = :id
                and t.user.username = :username
                and t.expiresAt >= CURRENT_TIMESTAMP
                and t.tokenType = 'JWT'
                and not(t.revoked)""")
    boolean isJWTValid(@Param("id") String id, @Param("username") String username);
}

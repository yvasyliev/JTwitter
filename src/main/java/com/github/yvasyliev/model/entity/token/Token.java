package com.github.yvasyliev.model.entity.token;

import com.github.yvasyliev.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Token {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiresAt = LocalDateTime.now().plusDays(1);

    @Column(nullable = false)
    private Boolean revoked = Boolean.FALSE;

    private boolean isExpired() {
        return LocalDateTime.now().isBefore(expiresAt);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", expiresAt=" + expiresAt +
                ", revoked=" + revoked +
                '}';
    }
}

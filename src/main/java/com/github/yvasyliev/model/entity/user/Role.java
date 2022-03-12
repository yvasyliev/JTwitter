package com.github.yvasyliev.model.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    UNCONFIRMED_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}

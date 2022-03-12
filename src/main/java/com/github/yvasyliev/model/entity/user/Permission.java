package com.github.yvasyliev.model.entity.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Permission {
    CREATE_POST("CREATE_POST"),
    DELETE_POST("DELETE_POST");

    private final GrantedAuthority grantedAuthority;

    Permission(GrantedAuthority grantedAuthority) {
        this.grantedAuthority = grantedAuthority;
    }

    Permission(String authority) {
        this(new SimpleGrantedAuthority(authority));
    }

    public GrantedAuthority getGrantedAuthority() {
        return grantedAuthority;
    }
}

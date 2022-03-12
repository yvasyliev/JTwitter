package com.github.yvasyliev.model.entity.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public enum Role {
    USER(Permission.CREATE_POST, Permission.DELETE_POST),
    ADMIN(Permission.CREATE_POST, Permission.DELETE_POST),
    UNCONFIRMED_USER;

    private final Set<Permission> permissions = new HashSet<>();

    Role(Collection<Permission> permissions) {
        this.permissions.addAll(permissions);
    }

    Role(Permission... permissions) {
        this(Arrays.asList(permissions));
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}

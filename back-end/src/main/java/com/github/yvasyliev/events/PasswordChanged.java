package com.github.yvasyliev.events;

import com.github.yvasyliev.model.entity.user.User;

public record PasswordChanged(String host, User user) {
}

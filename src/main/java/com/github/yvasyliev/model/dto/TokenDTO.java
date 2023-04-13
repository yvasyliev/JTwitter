package com.github.yvasyliev.model.dto;

import java.time.LocalDateTime;

public record TokenDTO(String token, LocalDateTime expiresAt) {
}

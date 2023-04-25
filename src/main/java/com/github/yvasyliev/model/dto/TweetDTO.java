package com.github.yvasyliev.model.dto;

import java.time.LocalDateTime;

public record TweetDTO(Long id, String text, LocalDateTime createdAt, Integer replies, Integer likes) {
}

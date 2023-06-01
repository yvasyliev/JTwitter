package com.github.yvasyliev.model.dto;

import com.github.yvasyliev.validation.TweetExists;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateTweetForm(
        @TweetExists
        Long parentTweetId,

        @NotEmpty
        @Size(min = 1, max = 256)
        String text
) {
}

package com.github.yvasyliev.model.dto;

import java.util.stream.Stream;

public record TweetPageDTO(Stream<TweetDTO> tweets, boolean hasMoreTweets) {
}

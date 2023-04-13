package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.CreateTweetForm;
import com.github.yvasyliev.model.dto.TweetDTO;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @PostMapping
    public Map<String, Long> createTweet(@RequestBody CreateTweetForm createTweetForm, Authentication authentication) {
        var tweet = tweetService.createTweet((User) authentication.getPrincipal(), createTweetForm);
        return Map.of("tweetId", tweet.getId());
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<?> deleteTweet(@PathVariable long tweetId) {
        tweetService.deleteTweet(tweetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetDTO> getTweetById(@PathVariable long tweetId) {
        return tweetService
                .findById(tweetId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

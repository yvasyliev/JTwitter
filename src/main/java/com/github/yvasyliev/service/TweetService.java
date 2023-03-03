package com.github.yvasyliev.service;

import com.github.yvasyliev.model.dto.TweetForm;
import com.github.yvasyliev.model.entity.Tweet;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.TweetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    @Transactional
    public Tweet createTweet(TweetForm tweetForm, User user) {
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setText(tweetForm.getText());
        return tweetRepository.save(tweet);
    }

    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    public List<Tweet> findByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);
    }
}

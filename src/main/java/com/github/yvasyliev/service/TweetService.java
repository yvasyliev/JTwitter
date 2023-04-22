package com.github.yvasyliev.service;

import com.github.yvasyliev.model.dto.CreateTweetForm;
import com.github.yvasyliev.model.dto.TweetDTO;
import com.github.yvasyliev.model.entity.Tweet;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.repository.TweetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    @Transactional
    public Tweet createTweet(User user, CreateTweetForm createTweetForm) {
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setText(createTweetForm.text());

        var parentTweetId = createTweetForm.parentTweetId();
        if (parentTweetId != null) {
            tweetRepository.findById(parentTweetId).ifPresent(tweet::setParentTweet);
        }

        return tweetRepository.save(tweet);
    }

    @Transactional
    public void deleteTweet(long tweetId) {
        tweetRepository.deleteById(tweetId);
    }

    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    public Optional<TweetDTO> findById(long tweetId) {
        return tweetRepository.findTweetDTOById(tweetId);
    }
}

package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.TweetForm;
import com.github.yvasyliev.model.entity.Tweet;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tweet")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @PostMapping
    @ResponseBody
    public Tweet postTweet(TweetForm tweetForm, Authentication authentication) {
        return tweetService.createTweet(
                tweetForm,
                (User) authentication.getPrincipal()
        );
    }
}

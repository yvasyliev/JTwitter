package com.github.yvasyliev.controller;

import com.github.yvasyliev.service.TweetService;
import com.github.yvasyliev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> home(Authentication authentication) {
        var result = new HashMap<String, Object>();
        result.put("tweets", tweetService.findAll());
        if (authentication != null) {
            result.put("user", authentication.getPrincipal());
        }
        return result;
    }
}

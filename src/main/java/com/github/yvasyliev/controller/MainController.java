package com.github.yvasyliev.controller;

import com.github.yvasyliev.service.TweetService;
import com.github.yvasyliev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
        model.addAttribute("tweets", tweetService.findAll());
        return "index";
    }
}

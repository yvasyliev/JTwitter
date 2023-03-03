package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.TweetService;
import com.github.yvasyliev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tweets", tweetService.findAll());
        return "index";
    }

    @GetMapping
    public String userPage(@RequestParam String username, Model model) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User '" + username + "' doesn't exist."));

        model.addAttribute("user", user);
        return "index"; 
    }
}

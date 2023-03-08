package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.SignupForm;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.model.event.UserRegisteredEvent;
import com.github.yvasyliev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping
    public String registrationView() {
        return "registration";
    }

    @PostMapping
    public String register(SignupForm signupForm, HttpServletRequest request) {
        User user = userService.register(signupForm);
        String url = request.getRequestURL()
                .toString()
                .replace(request.getRequestURI(), "");
        applicationEventPublisher.publishEvent(new UserRegisteredEvent(user, url));
        return "complete_registration";
    }

    @GetMapping("/complete")
    public String completeRegistration(@RequestParam String token) {
        userService.confirm(token);
        return "confirmation";
    }
}

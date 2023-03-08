package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.SignupForm;
import com.github.yvasyliev.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public Map<String, String> signup(@Valid @RequestBody SignupForm signupForm, HttpServletRequest ignoredRequest) {
        return Map.of("token", authenticationService.signup(signupForm));
    }


}

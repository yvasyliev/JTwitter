package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.SignupForm;
import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.auth.AuthenticationService;
import com.github.yvasyliev.validation.ValidEmailToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public Token signup(@Valid @RequestBody SignupForm signupForm, HttpServletRequest request) throws ServletException {
        var token = authenticationService.signup(signupForm);
        request.login(signupForm.getUsername(), signupForm.getPassword());
        return token;
    }

    @GetMapping("/confirm")
    public User confirm(@RequestParam @ValidEmailToken String token) {
        return authenticationService.confirm(token);
    }
}

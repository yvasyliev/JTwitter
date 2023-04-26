package com.github.yvasyliev.controller;

import com.github.yvasyliev.model.dto.SignInForm;
import com.github.yvasyliev.model.dto.SignUpForm;
import com.github.yvasyliev.model.dto.TokenDTO;
import com.github.yvasyliev.model.dto.UpdateEmailForm;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.EmailSender;
import com.github.yvasyliev.service.auth.AuthenticationService;
import com.github.yvasyliev.service.auth.TokenService;
import com.github.yvasyliev.validation.ValidEmailToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailSender emailSender;

    @PostMapping("/signUp")
    public TokenDTO signUp(@Valid @RequestBody SignUpForm signupForm, HttpServletRequest request) throws ServletException {
        var token = authenticationService.signUp(signupForm);
        request.login(signupForm.username(), signupForm.password());
        return new TokenDTO(token.getId(), token.getExpiresAt());
    }

    @GetMapping("/confirmEmail")
    public ResponseEntity<?> confirmEmail(@RequestParam @ValidEmailToken String tokenId) {
        authenticationService.confirmEmail(tokenId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signIn")
    public TokenDTO signIn(@RequestBody SignInForm signInForm, HttpServletRequest request) throws ServletException {
        request.login(signInForm.usernameOrEmail(), signInForm.password());
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var token = tokenService.createJwtToken((User) principal);
        return new TokenDTO(token.getId(), token.getExpiresAt());
    }

    @GetMapping("/signOut")
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        var jwt = request.getHeader(AUTHORIZATION).substring(BEARER_PREFIX.length());
        authenticationService.signOut(jwt);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sendEmailConfirmation")
    public ResponseEntity<?> sendEmailConfirmation(HttpServletRequest request, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        tokenService.revokeEmailToken(user);

        var token = tokenService.createJwtToken(user);
        emailSender.sendEmailConfirmation(request, token);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailForm updateEmailForm, HttpServletRequest request, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        authenticationService.updateEmail(updateEmailForm.email(), user);

        var emailToken = tokenService.createEmailToken(user);
        emailSender.sendEmailConfirmation(request, emailToken);

        return ResponseEntity.noContent().build();
    }
}

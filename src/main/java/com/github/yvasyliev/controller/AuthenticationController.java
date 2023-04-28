package com.github.yvasyliev.controller;

import com.github.yvasyliev.events.PasswordChanged;
import com.github.yvasyliev.events.EmailChanged;
import com.github.yvasyliev.model.dto.SignInForm;
import com.github.yvasyliev.model.dto.SignUpForm;
import com.github.yvasyliev.model.dto.TokenDTO;
import com.github.yvasyliev.model.dto.UpdateEmailForm;
import com.github.yvasyliev.model.dto.UpdatePasswordForm;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.auth.AuthenticationService;
import com.github.yvasyliev.service.auth.TokenService;
import com.github.yvasyliev.uitls.RequestUtils;
import com.github.yvasyliev.validation.ValidEmailToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/signUp")
    public TokenDTO signUp(@Valid @RequestBody SignUpForm signupForm, HttpServletRequest request) throws ServletException {
        var token = authenticationService.signUp(signupForm);
        request.login(signupForm.username(), signupForm.password());
        eventPublisher.publishEvent(new EmailChanged(RequestUtils.getHost(request), token.getUser()));
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

    @PostMapping("/signOut")
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        var jwt = request.getHeader(AUTHORIZATION).substring(BEARER_PREFIX.length());
        authenticationService.signOut(jwt);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sendEmailConfirmation")
    public ResponseEntity<?> sendEmailConfirmation(HttpServletRequest request, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        tokenService.revokeEmailToken(user);
        eventPublisher.publishEvent(new EmailChanged(RequestUtils.getHost(request), user));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailForm updateEmailForm, HttpServletRequest request, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        user = authenticationService.updateEmail(updateEmailForm.email(), user);
        eventPublisher.publishEvent(new EmailChanged(RequestUtils.getHost(request), user));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm, HttpServletRequest request, Authentication authentication) {
        var user = authenticationService.updatePassword(
                updatePasswordForm.newPassword(),
                (User) authentication.getPrincipal()
        );
        eventPublisher.publishEvent(new PasswordChanged(RequestUtils.getHost(request), user));
        return ResponseEntity.noContent().build();
    }
}

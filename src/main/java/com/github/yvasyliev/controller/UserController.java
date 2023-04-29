package com.github.yvasyliev.controller;

import com.github.yvasyliev.events.EmailChanged;
import com.github.yvasyliev.events.PasswordChanged;
import com.github.yvasyliev.model.dto.SignInForm;
import com.github.yvasyliev.model.dto.SignUpForm;
import com.github.yvasyliev.model.dto.TokenDTO;
import com.github.yvasyliev.model.dto.UpdateEmailForm;
import com.github.yvasyliev.model.dto.UpdateFirstNameForm;
import com.github.yvasyliev.model.dto.UpdateLastNameForm;
import com.github.yvasyliev.model.dto.UpdatePasswordForm;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.TokenService;
import com.github.yvasyliev.service.UserService;
import com.github.yvasyliev.uitls.RequestUtils;
import com.github.yvasyliev.validation.SupportedImageFormat;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public TokenDTO signUp(@Valid @RequestBody SignUpForm signupForm, HttpServletRequest request) throws ServletException {
        var token = userService.signUp(signupForm);
        request.login(signupForm.username(), signupForm.password());
        eventPublisher.publishEvent(new EmailChanged(RequestUtils.getHost(request), token.getUser()));
        return new TokenDTO(token.getId(), token.getExpiresAt());
    }

    @PostMapping("/email/confirm")
    public ResponseEntity<?> confirmEmail(@RequestParam @ValidEmailToken String tokenId) {
        userService.confirmEmail(tokenId);
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
        userService.signOut(jwt);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email/sendConfirmation")
    public ResponseEntity<?> sendEmailConfirmation(HttpServletRequest request, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        tokenService.revokeEmailToken(user);
        eventPublisher.publishEvent(new EmailChanged(RequestUtils.getHost(request), user));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailForm updateEmailForm, HttpServletRequest request, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        user = userService.updateEmail(updateEmailForm.email(), user);
        eventPublisher.publishEvent(new EmailChanged(RequestUtils.getHost(request), user));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm, HttpServletRequest request, Authentication authentication) {
        var user = userService.updatePassword(
                updatePasswordForm.newPassword(),
                (User) authentication.getPrincipal()
        );
        eventPublisher.publishEvent(new PasswordChanged(RequestUtils.getHost(request), user));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/photo")
    public ResponseEntity<?> setUserPhoto(@RequestParam("photo") @SupportedImageFormat MultipartFile photo, Authentication authentication) throws IOException {
        userService.setUserPhoto(photo, (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/firstName")
    public ResponseEntity<?> updateFirstName(@RequestBody UpdateFirstNameForm updateFirstNameForm, Authentication authentication) {
        userService.updateFirstName(updateFirstNameForm.firstName(), (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/lastName")
    public ResponseEntity<?> updateLastName(@RequestBody UpdateLastNameForm updateLastNameForm, Authentication authentication) {
        userService.updateLastName(updateLastNameForm.lastName(), (User) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}

package com.github.yvasyliev.events.listeners;

import com.github.yvasyliev.events.PasswordChanged;
import com.github.yvasyliev.events.EmailChanged;
import com.github.yvasyliev.service.EmailSender;
import com.github.yvasyliev.service.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SendEmailListener {
    private static final String EMAIL_CONFIRMATION_TEMPLATE = "Please follow the link to confirm email: %s";
    private static final String PASSWORD_CHANGED_TEMPLATE = "Your password has been changed. If it wasn't you, please reset your password now: %s";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailSender emailSender;

    @Async
    @EventListener
    public void sendEmailConfirmation(EmailChanged emailChanged) {
        var emailToken = tokenService.createEmailToken(emailChanged.user());
        var text = EMAIL_CONFIRMATION_TEMPLATE.formatted(
                UriComponentsBuilder
                        .fromHttpUrl(emailChanged.host())
                        .pathSegment("api", "v1", "auth", "confirm")
                        .queryParam("token", emailToken.getId())
                        .toUriString()
        );
        emailSender.send(emailChanged.user().getEmail(), "Email confirmation", text);
    }

    @Async
    @EventListener
    public void sendPasswordChangedEmail(PasswordChanged passwordChanged) {
        var text = PASSWORD_CHANGED_TEMPLATE.formatted(
                UriComponentsBuilder
                        .fromHttpUrl(passwordChanged.host())
                        .pathSegment("resetPassword")
                        .toUriString()
        );
        emailSender.send(passwordChanged.user().getEmail(), "Your password has been changed", text);
    }
}

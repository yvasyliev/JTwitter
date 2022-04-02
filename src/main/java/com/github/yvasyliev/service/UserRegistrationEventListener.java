package com.github.yvasyliev.service;

import com.github.yvasyliev.model.entity.ConfirmationToken;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.model.event.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationEventListener implements ApplicationListener<UserRegisteredEvent> {
    private static final String MESSAGE_TEMPLATE = "Please follow the link to complete registration: %s/registration/complete?token=%s";

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${SMTP_LOGIN}")
    private String from;

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        User user = event.getUser();
        ConfirmationToken confirmationToken = confirmationTokenService.createConfirmationToken(user);

        MailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("Complete registration");
        message.setText(String.format(
                MESSAGE_TEMPLATE,
                event.getUrl(),
                confirmationToken.getToken()
        ));
    }
}

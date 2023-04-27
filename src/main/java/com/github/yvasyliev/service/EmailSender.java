package com.github.yvasyliev.service;

import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class EmailSender {
    @Value("${SMTP_LOGIN}")
    private String from;

    @Autowired
    private BiFunction<HttpServletRequest, Token, String> emailConfirmationTextFactory;

    @Autowired
    private Function<HttpServletRequest, String> passwordChangedTextFactory;

    @Autowired
    private MailSender mailSender;

    @Async
    public void sendEmailConfirmation(HttpServletRequest request, Token token) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(token.getUser().getEmail());
        message.setSubject("Email confirmation");
        message.setText(emailConfirmationTextFactory.apply(request, token));
        // TODO: 3/8/2023 uncomment
//        mailSender.send(message);
    }

    @Async
    public void sendPasswordChangedEmail(HttpServletRequest request, User user) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("Your password has been changed");
        message.setText(passwordChangedTextFactory.apply(request));
        // TODO: 4/27/2023 uncomment
//        mailSender.send(message);
    }
}

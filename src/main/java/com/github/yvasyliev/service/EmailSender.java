package com.github.yvasyliev.service;

import com.github.yvasyliev.model.entity.token.Token;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class EmailSender {
    @Value("${SMTP_LOGIN}")
    private String from;

    @Autowired
    private BiFunction<HttpServletRequest, Token, String> emailConfirmationTextFactory;

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
}

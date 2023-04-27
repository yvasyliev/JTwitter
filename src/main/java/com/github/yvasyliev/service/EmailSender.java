package com.github.yvasyliev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Value("${SMTP_LOGIN}")
    private String from;

    @Autowired
    private MailSender mailSender;

    public void send(String to, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        // TODO: 4/27/2023 uncomment
//        mailSender.send(message);
    }
}

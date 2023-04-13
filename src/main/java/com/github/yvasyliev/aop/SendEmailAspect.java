package com.github.yvasyliev.aop;

import com.github.yvasyliev.model.entity.token.Token;
import com.github.yvasyliev.model.entity.user.User;
import com.github.yvasyliev.service.auth.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.util.UriComponentsBuilder;

@Aspect
@Configuration
public class SendEmailAspect {
    @Autowired
    private MailSender mailSender;

    @Autowired
    private TokenService tokenService;

    @Value("${SMTP_LOGIN}")
    private String from;

    @Async
    @AfterReturning(value = "execution(* com.github.yvasyliev.controller.AuthenticationController.signup(..)) && args(..,request)", returning = "token")
    public void sendEmailConfirmation(HttpServletRequest request, Token token) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(token.getUser().getEmail());
        message.setSubject("Email confirmation");
        message.setText(createText(request, token.getUser()));
        // TODO: 3/8/2023 uncomment
//        mailSender.send(message);
    }

    private String createText(HttpServletRequest request, User user) {
        return "Please follow the link to confirm email: " + UriComponentsBuilder
                .fromHttpUrl(getUrl(request))
                .pathSegment("api", "v1", "auth", "confirm")
                .queryParam("token", tokenService.createEmailToken(user).getId())
                .toUriString();
    }

    private String getUrl(HttpServletRequest request) {
        return request.getRequestURL()
                .toString()
                .replace(request.getRequestURI(), "");
    }
}

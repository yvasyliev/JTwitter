package com.github.yvasyliev.aop;

import com.github.yvasyliev.model.dto.TokenDTO;
import com.github.yvasyliev.repository.TokenRepository;
import com.github.yvasyliev.service.EmailSender;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Aspect
@Configuration
public class SendEmailAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailAspect.class);

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmailSender emailSender;

    @Async
    @AfterReturning(value = "execution(* com.github.yvasyliev.controller.AuthenticationController.signUp(..)) && args(..,request)", returning = "tokenDTO")
    public void sendEmailConfirmation(HttpServletRequest request, TokenDTO tokenDTO) {
        tokenRepository
                .findById(tokenDTO.token())
                .ifPresentOrElse(
                        token -> emailSender.sendEmailConfirmation(request, token),
                        () -> LOGGER.error("Token {} not found.", tokenDTO.token())
                );
    }
}

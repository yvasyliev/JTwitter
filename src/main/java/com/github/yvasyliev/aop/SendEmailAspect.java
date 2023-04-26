package com.github.yvasyliev.aop;

import com.github.yvasyliev.model.dto.TokenDTO;
import com.github.yvasyliev.service.EmailSender;
import com.github.yvasyliev.service.auth.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class SendEmailAspect {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailSender emailSender;

    @AfterReturning(value = "execution(* com.github.yvasyliev.controller.AuthenticationController.signUp(..)) && args(..,request)", returning = "tokenDTO")
    public void sendEmailConfirmation(HttpServletRequest request, TokenDTO tokenDTO) {
        var user = tokenService.getById(tokenDTO.token()).getUser();
        var emailToken = tokenService.createEmailToken(user);
        emailSender.sendEmailConfirmation(request, emailToken);
    }
}

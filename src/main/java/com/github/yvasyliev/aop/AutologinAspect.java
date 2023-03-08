package com.github.yvasyliev.aop;

import com.github.yvasyliev.model.dto.SignupForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AutologinAspect {
    @AfterReturning(value = "execution(* com.github.yvasyliev.controller.AuthenticationController.signup(..)) && args(signupForm,request)")
    public void autologin(SignupForm signupForm, HttpServletRequest request) throws ServletException {
        request.login(signupForm.getUsername(), signupForm.getPassword());
    }
}

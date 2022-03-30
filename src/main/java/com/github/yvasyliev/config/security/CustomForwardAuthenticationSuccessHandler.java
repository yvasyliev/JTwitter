package com.github.yvasyliev.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomForwardAuthenticationSuccessHandler extends ForwardAuthenticationSuccessHandler {
    public CustomForwardAuthenticationSuccessHandler(@Value("/") String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectTo = request.getParameter("redirect_to");

        response.sendRedirect(
                redirectTo == null || redirectTo.isEmpty()
                        ? "/"
                        : redirectTo
        );
    }
}

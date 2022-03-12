package com.github.yvasyliev.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomForwardAuthenticationSuccessHandler extends ForwardAuthenticationSuccessHandler {
    public CustomForwardAuthenticationSuccessHandler(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectTo = request.getParameter("redirect_to");

        if (redirectTo == null || redirectTo.isEmpty()) {
            super.onAuthenticationSuccess(request, response, authentication);
        }

        response.sendRedirect(redirectTo);
    }
}

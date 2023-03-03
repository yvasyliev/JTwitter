package com.github.yvasyliev.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomForwardAuthenticationSuccessHandler extends ForwardAuthenticationSuccessHandler {
    private final String forwardUrl;

    public CustomForwardAuthenticationSuccessHandler(@Value("/") String forwardUrl) {
        super(forwardUrl);
        this.forwardUrl = forwardUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectTo = request.getParameter("redirect_to");

        response.sendRedirect(
                redirectTo == null || redirectTo.isEmpty()
                        ? forwardUrl
                        : redirectTo
        );
    }
}

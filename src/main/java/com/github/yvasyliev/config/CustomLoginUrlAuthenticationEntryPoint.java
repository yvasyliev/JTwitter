package com.github.yvasyliev.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public CustomLoginUrlAuthenticationEntryPoint(@Value("/login") String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String redirectTo = UrlUtils.buildRequestUrl(request);
        String url = super.determineUrlToUseForThisRequest(request, response, exception);
        return UriComponentsBuilder.fromPath(url).queryParam("redirect_to", redirectTo).toUriString();
    }
}

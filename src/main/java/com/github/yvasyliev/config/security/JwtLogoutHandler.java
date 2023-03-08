package com.github.yvasyliev.config.security;

import com.github.yvasyliev.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutHandler implements LogoutHandler {
    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var authorization = request.getHeader(AUTHORIZATION);
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            var jwt = authorization.substring(BEARER_PREFIX.length());
            var optionalToken = tokenRepository.findById(jwt);
            if (optionalToken.isPresent()) {
                var token = optionalToken.get();
                token.setRevoked(true);
                tokenRepository.save(token);
                response.setStatus(HttpStatus.NO_CONTENT.value());
                return;
            }
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}

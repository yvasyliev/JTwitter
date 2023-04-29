package com.github.yvasyliev.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService {
    @Autowired
    private Key signingKey;

    @Autowired
    private JwtParser jwtParser;

    public Claims getBody(String jwt) {
        return jwtParser.parseClaimsJws(jwt).getBody();
    }

    public String createJwt(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusDays(1)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isExpired(String jwt) {
        return getBody(jwt).getExpiration().before(new Date());
    }

    public String getSubject(String jwt) {
        return getBody(jwt).getSubject();
    }
}

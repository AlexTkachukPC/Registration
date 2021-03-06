package com.example.registration.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    @Override
    public String generateToken(String username, Collection<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setExpiration(new Date(Instant.now().plusSeconds(jwtProperties.getTtl()).toEpochMilli()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret().getBytes())
                .compact();
    }

    @Override
    public Claims parseToken(String jwt) {

        Claims claims = Jwts.parser()
            .setSigningKey(jwtProperties.getSecret().getBytes())
            .parseClaimsJws(jwt)
            .getBody();

        String username = claims.getSubject();
        if (username == null || username.isEmpty()) {
            throw new JwtException("Missing JWT subject");
        }

        return claims;
    }
}

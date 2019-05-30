package com.hf.cursomc.security;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long tokenExpiration;

    public String generateToken(String email) {
        String jwtToken = Jwts.builder().setSubject(email).setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes()).compact();
        return jwtToken;
    }

    public boolean tokenValido(String jwtToken) {
        Claims claims = getClaims(jwtToken);

        if (Objects.nonNull(claims)) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            if (Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate)) {
                return true;
            }

        }

        return false;
    }

    private Claims getClaims(String jwtToken) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String jwtToken) {
        Claims claims = getClaims(jwtToken);
        if (Objects.nonNull(claims)) {
            return claims.getSubject();
        }
        return null;
    }

}

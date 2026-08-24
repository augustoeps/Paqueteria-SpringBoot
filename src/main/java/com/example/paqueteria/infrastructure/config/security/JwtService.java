package com.example.paqueteria.infrastructure.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

    private final SecretKey secretKey;
    private final long expiracionMs;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiracionMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiracionMs = expiracionMs;
    }

    public String generarToken(String username, String rol) {
        return Jwts.builder()
                .subject(username)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiracionMs))
                .signWith(secretKey)
                .compact();
    }

    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public String extraerRol(String token) {
        return extraerClaim(token, claims -> claims.get("rol", String.class));
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    public boolean esTokenValido(String token, String username) {
        String usernameDelToken = extraerUsername(token);
        return usernameDelToken.equals(username) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        Date expiracion = extraerClaim(token, Claims::getExpiration);
        return expiracion.before(new Date());
    }
}
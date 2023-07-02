package com.mohacel.springsecurity.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final String SECRET_KEY = "HXtMr3N$oyjV*OA)Q1{A?vDi9MFfetXu3l0B(M{{0lcAuWFZ5r*K!AO1OJ#na)*0K@)9Y5JR9RVF{0U=G!t7C]2}9D?fm9GaNMC6hi!djGH71kaG!E7oTy-ynVh)G(&h]8+Yk]nT+TqSc3M8bbrL!&OW8Lwvu6Shi=d3HUeGplKKpEr45Dy#eTmW-g0Wva=2tiNUk=lsd2ELhZ-GL{I7eMLDIEtYFMFcb[xlW=F)pRhxC$-sh5Vg(!Pg{NnK$DGL";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userEmail);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))  // Expire time 5 hours
                .signWith(getSignKey())
                .compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

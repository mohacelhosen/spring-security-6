package com.mohacel.springsecurity.service.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil {

    private final String SECRET_KEY = "4858744d72334e246f796a562a4f412951317b413f764469394d466665745875336c3042284d7b7b306c63417557465a35722a4b21414f314f4a236e61292a304b40293959354a52395256467b30553d47217437435d327d39443f666d3947614e4d4336686921646a474837316b61472145376f54792d796e566829472826685d382b596b5d6e542b54715363334d386262724c21264f57384c777675365368693d643348556547706c4b4b704572343544792365546d572d67305776613d3274694e556b3d6c736432454c685a2d474c7b4937654d4c4449457459464d4663625b786c573d46297052687843242d7368355667282150677b4e6e4b2444474c";
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims allClaims = extractAllClaims(token);
        return claimsResolver.apply(allClaims);
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

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String generateToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, userEmail);
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        log.info("JwtTokenUtil:createToken");
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

package com.mohacel.springsecurity.service;

import com.mohacel.springsecurity.entity.UserEntity;
import com.mohacel.springsecurity.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;


@Service
public class JwtService {
    @Autowired
    private UserRepository userRepository;
    private static final long EXPIRE_TIME = 1000*60*5;
    private final String SECRET_KEY="377529A78E6E4527AA41795AFE99D";

    public String generateToken(String email) {
        Optional<UserEntity> user = userRepository.findUserEntityByEmail(email);

        Map<String, Object> claims = new HashMap<>();
        if (user.isPresent()){
            claims.put("name", user.get().getName());
            claims.put("role", user.get().getRole());
        }
        String token = createToken(claims, email);
        return token;
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

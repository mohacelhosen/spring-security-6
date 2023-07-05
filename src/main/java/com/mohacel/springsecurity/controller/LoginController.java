package com.mohacel.springsecurity.controller;

import com.mohacel.springsecurity.dto.LoginInfo;
import com.mohacel.springsecurity.service.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private JwtTokenUtil jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/authenticate")
    public ResponseEntity<?> login(@RequestBody LoginInfo userLoginInfo) {
        log.info("⭕ LoginController:login Login Info=  "+userLoginInfo.toString());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginInfo.getEmail(), userLoginInfo.getPassword()));
        log.info("⭕ LoginController:login authenticated object:: "+authentication.toString());
        if (authentication.isAuthenticated()) {
            log.info("LoginController:login, is authenticated::"+authentication.isAuthenticated());
            String token = jwtService.generateToken(userLoginInfo.getEmail());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        throw new BadCredentialsException("Invalid email or password!");
    }
}

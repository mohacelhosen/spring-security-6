package com.mohacel.springsecurity.controller;

import com.mohacel.springsecurity.dto.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @GetMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody LoginUser user){
        return null;
    }

}

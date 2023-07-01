package com.mohacel.springsecurity.controller;

import com.mohacel.springsecurity.dto.LoginInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody LoginInfo userLoginInfo){
        return null;
    }

}

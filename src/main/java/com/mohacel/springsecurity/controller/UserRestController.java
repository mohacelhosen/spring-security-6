package com.mohacel.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome the world of Spring rest API 😇";
    }

    @GetMapping("/test")
    public String test(){
        String msg = "Authentication Accepted ✅";
        return msg;
    }
}

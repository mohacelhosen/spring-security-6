package com.mohacel.springsecurity.controller;

import com.mohacel.springsecurity.dto.UserDto;
import com.mohacel.springsecurity.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private static final Logger logger = LogManager.getLogger(UserRestController.class);

    private IUserService service;

    @Autowired
    public void setService(IUserService service) {
        this.service = service;
    }


    @GetMapping("/welcome")
    public String welcome() {
        logger.info("UserRestController:welcome executed");
        return "Welcome the world of Spring rest API 😇";
    }

    @GetMapping("/test")
    public String test() {
        return "Authentication Accepted ✅";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        logger.info("UserRestController:register execution started....");
        logger.info("UserRestController:register request payload {} ", user);
        boolean register = service.register(user);
        String message = null;
        if (register) {
            message = "Registration Successfully ✅";
        } else {
            message = "Registration Fail ❌";
        }
        logger.info("UserRestController:register execution ended....");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        UserDto userById = service.findUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> allUser() {
        List<UserDto> userDtoList = service.allUser();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
}

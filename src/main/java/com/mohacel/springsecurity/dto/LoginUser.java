package com.mohacel.springsecurity.dto;

import lombok.Data;

@Data
public class LoginUser {
    private String email;
    private String password;
}

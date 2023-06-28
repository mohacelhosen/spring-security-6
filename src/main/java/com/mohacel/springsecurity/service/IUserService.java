package com.mohacel.springsecurity.service;

import com.mohacel.springsecurity.dto.UserDto;

import java.util.List;

public interface IUserService {
    public boolean register(UserDto user);
    public UserDto findUserById(Integer userId);
    public List<UserDto> allUser();
}

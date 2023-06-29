package com.mohacel.springsecurity.service;

import com.mohacel.springsecurity.dto.UserDto;
import com.mohacel.springsecurity.entity.UserEntity;
import com.mohacel.springsecurity.exception.InvalidUserIdException;
import com.mohacel.springsecurity.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService{

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getDesignation().equalsIgnoreCase("admin")){
            userEntity.setRole("ROLE_ADMIN,ROLE_USER");
        }else {
            userEntity.setRole("ROLE_USER");
        }
        System.out.println(userEntity);
        UserEntity registeredUser = repository.save(userEntity);
        System.out.println(registeredUser);
        return registeredUser.getUserId() !=null;
    }

    @Override
    public UserDto findUserById(Integer userId) {
        UserDto user = new UserDto();
        UserEntity userEntity = repository.findById(userId)
                .orElseThrow(() -> new InvalidUserIdException("Invalid User ID ❌"));
        BeanUtils.copyProperties(userEntity, user);
        System.out.println(user);
        return  user;
    }

    @Override
    public List<UserDto> allUser() {
        List<UserEntity> all = repository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(UserEntity singleUser:all){
            UserDto tempUser = new UserDto();
            BeanUtils.copyProperties(singleUser,tempUser);
            System.out.println(tempUser);
            userDtoList.add(tempUser);
        }
        return userDtoList;
    }
}

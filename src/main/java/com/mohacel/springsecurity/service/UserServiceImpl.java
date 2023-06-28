package com.mohacel.springsecurity.service;

import com.mohacel.springsecurity.dto.UserDto;
import com.mohacel.springsecurity.entity.UserEntity;
import com.mohacel.springsecurity.exception.InvalidUserIdException;
import com.mohacel.springsecurity.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    private final UserRepository repository;
    @Autowired
    public UserServiceImpl(UserRepository repository){
        this.repository =repository;
    }

    @Override
    public boolean register(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        System.out.println(userEntity);
        return repository.save(userEntity).getUserId() !=null;
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

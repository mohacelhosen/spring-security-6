package com.mohacel.springsecurity.service;

import com.mohacel.springsecurity.entity.UserEntity;
import com.mohacel.springsecurity.exception.InvalidCredentialsException;
import com.mohacel.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = repository.findUserEntityByEmail(email);

        if (user !=null){
            return new User(user.getEmail(),  user.getPassword(), new ArrayList<>());
        }else{
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

    }
}

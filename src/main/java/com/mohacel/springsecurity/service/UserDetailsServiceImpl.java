package com.mohacel.springsecurity.service;

import com.mohacel.springsecurity.entity.UserEntity;
import com.mohacel.springsecurity.exception.InvalidCredentialsException;
import com.mohacel.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            return new User(user.getEmail(),  user.getPassword(), authority(user.getRole()));
        }else{
            throw new InvalidCredentialsException("Invalid Email or Password");
        }
    }

    public List<SimpleGrantedAuthority> authority(String userRole){
        List<SimpleGrantedAuthority> roles = Arrays.stream(userRole.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return roles;
    }
}

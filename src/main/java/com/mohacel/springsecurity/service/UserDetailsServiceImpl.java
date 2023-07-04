package com.mohacel.springsecurity.service;

import com.mohacel.springsecurity.entity.UserEntity;
import com.mohacel.springsecurity.exception.InvalidCredentialsException;
import com.mohacel.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = repository.findUserEntityByEmail(email);

        if (user.isPresent()) {
            return new User(user.get().getEmail(), user.get().getPassword(), authorities(user.get().getRole()));
        } else {
            throw new InvalidCredentialsException("Invalid email or Password");
        }
    }

    public List<SimpleGrantedAuthority> authorities(String roles) {
        return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}

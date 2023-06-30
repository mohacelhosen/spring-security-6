package com.mohacel.springsecurity.repository;

import com.mohacel.springsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public Optional<UserEntity> findUserEntityByEmail(String email);
}

package com.example.usercrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usercrud.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    
}

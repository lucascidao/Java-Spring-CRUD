package com.example.usercrud.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usercrud.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    
}

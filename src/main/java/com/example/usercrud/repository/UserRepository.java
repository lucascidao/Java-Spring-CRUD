package com.example.usercrud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.example.usercrud.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    public default ResponseEntity<User> updateUser( Long id, User user){
          Optional<User> existingUser = this.findById(id);
        if (existingUser.isPresent()) {
            User newUser = existingUser.get();

            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setAge(user.getAge());
            this.save(newUser);
            return ResponseEntity.ok(newUser);

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

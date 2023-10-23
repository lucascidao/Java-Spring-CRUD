package com.example.usercrud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usercrud.model.User;
import com.example.usercrud.repository.UserRepository;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> index() {
        return userRepository.findAll();
    }

    @PostMapping
    public void store(@RequestBody User user) {
        try {
            userRepository.save(user);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // Find the user by ID
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User newUser = existingUser.get();

            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setAge(user.getAge());
            userRepository.save(newUser);
            return ResponseEntity.ok(newUser);

        } else {
            return ResponseEntity.notFound().build();
        }

        // Save the updated user

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Optional<User> show(@PathVariable Long id) {
        return userRepository.findById(id);
    }
}

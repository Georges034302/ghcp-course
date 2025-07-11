package com.example.UserApp.service;

import com.example.UserApp.model.User;
import com.example.UserApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> getUserByEmail(String email) {
        return repo.findById(email);
    }

    // Add saveUser method
    public User saveUser(User user) {
        return repo.save(user);
    }

    // Add updateUser method (optional)
    public User updateUser(User user) {
        return repo.save(user);
    }

    // Add deleteUserByEmail method
    public void deleteUserByEmail(String email) {
        repo.deleteById(email);
    }
}
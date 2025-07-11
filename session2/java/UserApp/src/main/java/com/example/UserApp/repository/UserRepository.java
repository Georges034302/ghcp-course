package com.example.UserApp.repository;

import com.example.UserApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // No need to override findById; JpaRepository already provides it
    // You can add custom queries if needed, e.g.:
    // Optional<User> findByEmail(String email);
}
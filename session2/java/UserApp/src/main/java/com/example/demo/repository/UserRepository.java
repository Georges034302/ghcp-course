// repository/UserRepository.java
package com.example.userapp.repository;

import com.example.userapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
package com.example.UserApp.controller;

import com.example.UserApp.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Value("${app.api.key}")
    private String apiKey;

    /**
     * Retrieves a user by email from the H2 database.
     *
     * @param email the email address of the user to search for
     * @return ResponseEntity with "User Found" if the user exists, or 404 Not Found if not
     * @throws SQLException if a database access error occurs
     */
    @GetMapping("/user")
    public ResponseEntity<String> getUser(@RequestParam String email) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() ? ResponseEntity.ok("User Found")
                : ResponseEntity.notFound().build();
    }

    /**
     * Creates a new user in the H2 database.
     *
     * @param user the User object containing name and email
     * @return ResponseEntity with a success message
     * @throws SQLException if a database access error occurs
     */
    @PostMapping("/user")
    public ResponseEntity<String> createUserInDb(@RequestBody User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)");
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getEmail());
        pstmt.executeUpdate();
        return ResponseEntity.ok("User created successfully");
    }

    /**
     * Updates an existing user in the H2 database.
     *
     * @param user the User object containing updated name, email, and id
     * @return ResponseEntity with a success message
     * @throws SQLException if a database access error occurs
     */
    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody User user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET name = ?, email = ? WHERE id = ?");
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getEmail());
        pstmt.setLong(3, user.getId());
        pstmt.executeUpdate();
        return ResponseEntity.ok("User updated successfully");
    }

    /**
     * Deletes a user by email from the H2 database.
     *
     * @param email the email address of the user to delete
     * @return ResponseEntity with a success message
     * @throws SQLException if a database access error occurs
     */
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam String email) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE email = ?");
        pstmt.setString(1, email);
        pstmt.executeUpdate();
        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * Example: Get user by email
     *
     * @param email the email address of the user to search for
     * @return ResponseEntity with the User object if found, or 404 Not Found if not
     */
    @GetMapping("/api/user")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            // Use getEmail() for entity ID
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Example: Create user
     *
     * @param user the User object to create
     * @return ResponseEntity with the created User object
     */
    @PostMapping("/api/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Use setEmail() for entity ID
        User created = userService.saveUser(user);
        return ResponseEntity.ok(created);
    }
}
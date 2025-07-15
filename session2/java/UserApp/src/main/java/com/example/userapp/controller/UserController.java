package com.example.userapp.controller;

import com.example.userapp.model.User;
import com.example.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Inject API key from application.properties
    @Value("${app.api.key}")
    private String apiKey;

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the unique identifier of the user (path variable)
     * @return the User object if found, or null if not found
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.orElse(null);
    }

    /**
     * Retrieves a user by their email address using a secure PreparedStatement.
     *
     * @param email the email address of the user (query parameter)
     * @return the User object if found, or null if not found
     * @throws SQLException if a database access error occurs
     */
    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam String email) {
        User user = null;
        try (
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:userdb", "sa", "");
            PreparedStatement ps = conn.prepareStatement("SELECT id, email, name FROM user WHERE email = ?");
        ) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
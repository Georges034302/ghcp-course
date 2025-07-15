// model/User.java
package com.example.userapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;
    private String email;
    private String name;

    // ✅ Test case: API key pattern
    private static final String API_KEY = "sk_test_abc123";

    // ✅ Test case: Token pattern
    private static final String TOKEN = "token_1234567890abcdef";

    // ✅ Test case: Secret pattern
    private static final String SECRET = "apikey_secretvalue";

    // ✅ Test case: Password pattern
    private static final String PASSWORD = "myS3cretP@ssw0rd";

    // ✅ Test case: Base64-like string
    private static final String ENCODED = "dGhpcyBpcyBhIHZlcnlMb25nU3RyaW5nQmFzZTY0";

    // ❌ Should NOT match (not a sensitive field name)
    private static final String DESCRIPTION = "This is a regular description.";

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
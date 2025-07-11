package com.example.UserApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private String email;
    private String name;
    private String id;

    public User() {}

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
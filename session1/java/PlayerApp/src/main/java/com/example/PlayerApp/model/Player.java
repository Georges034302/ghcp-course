package com.example.PlayerApp.model;

import java.util.Random;

public class Player {
    private String id;      // 3-digit string "000"–"999"
    private String name;    // "Player-<random 0-100>"
    private int score;      // random 0–100

    // No-argument constructor: generates random values
    public Player() {
        Random rand = new Random();
        this.id = String.format("%03d", rand.nextInt(1000));
        this.name = "Player-" + rand.nextInt(101);
        this.score = rand.nextInt(101);
    }

    // Parameterized constructor: set all fields manually
    public Player(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}

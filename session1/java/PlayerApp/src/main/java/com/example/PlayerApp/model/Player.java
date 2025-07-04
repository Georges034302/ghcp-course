package com.example.PlayerApp.model;

public class Player {
    private String id;
    private String name;
    private int score;

    public Player() {
        this.id = String.format("%03d", (int) (Math.random() * 1000));
        this.name = "Player-" + (int) (Math.random() * 101);
        this.score = (int) (Math.random() * 101);
    }

    public Player(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

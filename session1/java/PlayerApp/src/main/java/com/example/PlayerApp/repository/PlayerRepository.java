package com.example.PlayerApp.repository;

import com.example.PlayerApp.model.Player;
import java.util.List;

// Repository interface for Player with getById and getAll methods
public interface PlayerRepository {
    Player getById(String id);
    List<Player> getAll();
}

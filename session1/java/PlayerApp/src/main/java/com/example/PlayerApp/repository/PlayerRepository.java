package com.example.PlayerApp.repository;

import com.example.PlayerApp.model.Player;
import java.util.List;

public interface PlayerRepository {
    Player getById(String id);
    List<Player> getAll();
    Player save(Player player);
}

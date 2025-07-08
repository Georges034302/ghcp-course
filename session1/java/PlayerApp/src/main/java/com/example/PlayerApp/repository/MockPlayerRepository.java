package com.example.PlayerApp.repository;

import com.example.PlayerApp.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockPlayerRepository implements PlayerRepository {
    private final List<Player> players = new ArrayList<>();

    public MockPlayerRepository() {
        for (int i = 0; i < 5; i++) {
            players.add(new Player());
        }
    }

    @Override
    public Player getById(String id) {
        return players.stream()
                .filter(player -> player.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Player> getAll() {
        return players;
    }

    @Override
    public Player save(Player player) {
        players.add(player);
        return player;
    }
}

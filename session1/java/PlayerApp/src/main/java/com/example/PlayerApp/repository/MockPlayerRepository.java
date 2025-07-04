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
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public List<Player> getAll() {
        return players;
    }
}

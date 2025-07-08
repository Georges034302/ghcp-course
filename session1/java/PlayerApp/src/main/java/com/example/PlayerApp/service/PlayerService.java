package com.example.PlayerApp.service;

import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getPlayerById(String id) {
        return playerRepository.getById(id);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.getAll();
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }
}

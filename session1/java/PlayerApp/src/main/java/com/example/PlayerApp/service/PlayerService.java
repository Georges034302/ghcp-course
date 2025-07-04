package com.example.PlayerApp.service;

import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.repository.PlayerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

// Service that uses PlayerRepository
@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player getById(String id) {
        return repository.getById(id);
    }

    public List<Player> getAll() {
        return repository.getAll();
    }
}

package com.example.PlayerApp.controller;

import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable String id) {
        Player player = playerService.getPlayerById(id);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }
}

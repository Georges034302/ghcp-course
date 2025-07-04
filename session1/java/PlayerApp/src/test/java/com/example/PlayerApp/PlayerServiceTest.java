package com.example.PlayerApp;

import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.repository.PlayerRepository;
import com.example.PlayerApp.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    private PlayerRepository repository;
    private PlayerService service;
    private Faker faker;

    @BeforeEach
    void setUp() {
        repository = mock(PlayerRepository.class);
        service = new PlayerService(repository);
        faker = new Faker();
    }

    @Test
    void getAllReturnsNonEmptyList() {
        List<Player> players = new ArrayList<>();
        players.add(new Player());
        when(repository.getAll()).thenReturn(players);

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void getPlayerReturnsCorrectPlayer() {
        Player player = new Player();
        when(repository.getById(player.getId())).thenReturn(player);

        assertEquals(player, service.getById(player.getId()));
    }

    @Test
    void getPlayerReturnsNullForUnknownId() {
        when(repository.getById("999")).thenReturn(null);

        assertNull(service.getById("999"));
    }

    @Test
    void addPlayerWithFakerNameAndScore() {
        String name = faker.name().fullName();
        int score = faker.number().numberBetween(0, 100);
        Player player = new Player();
        player.setName(name);
        player.setScore(score);

        assertNotNull(player.getName());
        assertTrue(player.getScore() >= 0 && player.getScore() <= 100);
    }
}

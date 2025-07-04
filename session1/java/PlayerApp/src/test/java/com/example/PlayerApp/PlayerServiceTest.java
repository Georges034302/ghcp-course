package com.example.PlayerApp;

import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.repository.PlayerRepository;
import com.example.PlayerApp.service.PlayerService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        when(repository.getAll()).thenReturn(Arrays.asList(new Player(), new Player()));
        List<Player> players = service.getAll();
        assertNotNull(players);
        assertTrue(players.size() > 0);
    }

    @Test
    void getPlayerReturnsCorrectPlayer() {
        Player player = new Player();
        when(repository.getById(player.getId())).thenReturn(player);
        Player found = service.getById(player.getId());
        assertNotNull(found);
        assertEquals(player.getId(), found.getId());
    }

    @Test
    void getPlayerReturnsNullForUnknownId() {
        when(repository.getById("999")).thenReturn(null);
        Player found = service.getById("999");
        assertNull(found);
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

    @Test
    void customModelConstructorWithFaker() {
        String id = String.format("%03d", faker.number().numberBetween(0, 1000));
        String name = "Player-" + faker.number().numberBetween(0, 101);
        int score = faker.number().numberBetween(0, 101);
        Player player = new Player(id, name, score);
        assertNotNull(player.getId());
        assertNotNull(player.getName());
        assertTrue(player.getScore() >= 0 && player.getScore() <= 100);
    }
}

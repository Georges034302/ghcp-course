package com.example.PlayerApp;

import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.repository.PlayerRepository;
import com.example.PlayerApp.service.PlayerService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    private PlayerRepository playerRepository;
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        playerService = new PlayerService(playerRepository);
    }

    @Test
    void testNoArgConstructor() {
        Player player = new Player();
        assertNotNull(player.getId());
        assertTrue(player.getName().startsWith("Player-"));
        assertTrue(player.getScore() >= 0 && player.getScore() <= 100);
    }

    @Test
    void testCustomConstructor() {
        Player player = new Player("123", "TestPlayer", 42);
        assertEquals("123", player.getId());
        assertEquals("TestPlayer", player.getName());
        assertEquals(42, player.getScore());
    }

    @Test
    void testGetAllPlayersReturnsNonEmptyList() {
        List<Player> players = Arrays.asList(new Player("1", "A", 10), new Player("2", "B", 20));
        when(playerRepository.getAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void testGetPlayerByIdReturnsCorrectPlayer() {
        Player player = new Player("123", "Test", 50);
        when(playerRepository.getById("123")).thenReturn(player);

        Player result = playerService.getPlayerById("123");
        assertNotNull(result);
        assertEquals("123", result.getId());
    }

    @Test
    void testGetPlayerByIdReturnsNullForUnknownId() {
        when(playerRepository.getById("999")).thenReturn(null);

        Player result = playerService.getPlayerById("999");
        assertNull(result);
    }

    @Test
    void testAddPlayerWithFaker() {
        Faker faker = new Faker();
        String id = String.format("%03d", faker.number().numberBetween(0, 1000));
        String name = faker.name().firstName();
        int score = faker.number().numberBetween(0, 101);

        Player player = new Player(id, name, score);
        when(playerRepository.save(player)).thenReturn(player);

        Player saved = playerService.addPlayer(player);
        assertEquals(id, saved.getId());
        assertEquals(name, saved.getName());
        assertEquals(score, saved.getScore());
    }
}

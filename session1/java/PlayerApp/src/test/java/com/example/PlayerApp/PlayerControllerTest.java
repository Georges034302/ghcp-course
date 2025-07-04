package com.example.PlayerApp;

import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.github.javafaker.Faker;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(controllers = com.example.PlayerApp.controller.PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService service;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void getAllReturnsNonEmptyList() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(new Player(), new Player()));
        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getPlayerReturnsCorrectPlayer() throws Exception {
        Player player = new Player();
        when(service.getById(player.getId())).thenReturn(player);

        mockMvc.perform(get("/api/player/" + player.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getPlayerReturnsNotFoundForUnknownId() throws Exception {
        when(service.getById("999")).thenReturn(null);

        mockMvc.perform(get("/api/player/999"))
                .andExpect(status().isNotFound());
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

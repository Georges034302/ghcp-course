package com.example.PlayerApp;

import com.example.PlayerApp.controller.PlayerController;
import com.example.PlayerApp.model.Player;
import com.example.PlayerApp.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    private List<Player> playerList;

    @BeforeEach
    void setUp() {
        playerList = Arrays.asList(
                new Player("001", "Player-1", 10),
                new Player("002", "Player-2", 20)
        );
    }

    @Test
    void testGetAllPlayersReturnsNonEmptyList() throws Exception {
        when(playerService.getAllPlayers()).thenReturn(playerList);

        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(playerList.size()))
                .andExpect(jsonPath("$[0].id").value("001"));
    }

    @Test
    void testGetPlayerByIdReturnsCorrectPlayer() throws Exception {
        Player player = playerList.get(0);
        when(playerService.getPlayerById("001")).thenReturn(player);

        mockMvc.perform(get("/api/player/001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("001"))
                .andExpect(jsonPath("$.name").value("Player-1"));
    }

    @Test
    void testGetPlayerByIdReturns404ForUnknownId() throws Exception {
        when(playerService.getPlayerById(anyString())).thenReturn(null);

        mockMvc.perform(get("/api/player/999"))
                .andExpect(status().isNotFound());
    }
}

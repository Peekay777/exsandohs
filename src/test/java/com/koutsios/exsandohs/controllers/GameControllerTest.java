package com.koutsios.exsandohs.controllers;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.exsandohs.dto.NewGameDto;
import com.koutsios.exsandohs.exception.CreateGameException;
import com.koutsios.exsandohs.exception.GameNotFoundException;
import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.NotCurrentPlayerException;
import com.koutsios.exsandohs.exception.PlayerNotFoundException;
import com.koutsios.exsandohs.model.player.ComputerPlayer;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.player.HumanPlayer;
import com.koutsios.exsandohs.model.player.Player;
import com.koutsios.exsandohs.service.GameServiceImpl;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
@MockBeans( {
    @MockBean(ComputerPlayer.class)
})
public class GameControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private GameServiceImpl gameService;

  @Mock
  private ComputerPlayer computerPlayer;

  @Before
  public void init() {
  }

  @Test
  public void createGame_whenCallingNewGame_thenReturnGameId() throws Exception {
    Player ex = HumanPlayer.builder()
        .name("One")
        .build();
    Player oh = new ComputerPlayer();
    NewGameDto newGameDto = NewGameDto.builder()
        .playerEx(ex)
        .playerOh(oh)
        .build();
    Game game = Game.builder().build();

    RequestBuilder request = post("/game")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newGameDto));
    when(gameService.createGame(any(NewGameDto.class))).thenReturn(game);

    MvcResult mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andReturn();
    Game actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Game.class);

    assertThat(actual, instanceOf(Game.class));
    verify(gameService, times(1)).createGame(any(NewGameDto.class));
  }

  @Test
  public void createGame_givenFailedGameCreation_whenCallingNewGame_thenReturn400() throws Exception {

    Player ex = HumanPlayer.builder()
        .name("One")
        .build();
    Player oh = new ComputerPlayer();
    NewGameDto newGameDto = NewGameDto.builder()
        .playerEx(ex)
        .playerOh(oh)
        .build();
    RequestBuilder request = post("/game")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newGameDto));
    when(gameService.createGame(any(NewGameDto.class))).thenThrow(CreateGameException.class);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void getGame_giveAnExistingGameId_whenGettingGame_thenReturnGame() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    Game game = Game.builder()
        .id(gameId)
        .build();
    when(gameService.getGame(anyString())).thenReturn(game);
    RequestBuilder request = get("/game/" + gameId);

    MvcResult mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andReturn();
    Game actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Game.class);

    assertThat(actual, instanceOf(Game.class));
    verify(gameService, times(1)).getGame(anyString());
  }

  @Test
  public void getGame_givenGameIdDoesNotExist_whenGettingGame_thenReturn404() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    RequestBuilder request = get("/game/" + gameId);
    when(gameService.getGame(anyString())).thenThrow(GameNotFoundException.class);

    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_whenTakingATurn_thenReturnGame() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = "00";
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    Game game = Game.builder()
        .id(gameId)
        .build();
    when(gameService.takeTurn(anyString(),anyString(),anyString()))
        .thenReturn(game);

    MvcResult mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andReturn();
    Game actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Game.class);

    assertThat(actual, instanceOf(Game.class));
    verify(gameService, times(1)).takeTurn(anyString(), anyString(), anyString());
  }

  @Test
  public void takeTurn_givenComputerPlayer_whenTakingTurn_thenReturnGame() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    RequestBuilder request = put("/game/{0}/{1}",gameId, playerName);
    Game game = Game.builder()
        .id(gameId)
        .build();
    when(gameService.takeTurn(anyString(),anyString(),isNull()))
        .thenReturn(game);

    MvcResult mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andReturn();
    Game actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Game.class);

    assertThat(actual, instanceOf(Game.class));
    verify(gameService, times(1)).takeTurn(anyString(), anyString(), isNull());
  }

  @Test
  public void takeTurn_givenGameIdDoesNotExist_whenTakingTurn_thenReturn404() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = "00";
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),anyString())).thenThrow(GameNotFoundException.class);

    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_givenGameIdDoesNotExistForComputerPlayer_whenTakingTurn_thenReturn404() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = null;
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),isNull())).thenThrow(GameNotFoundException.class);

    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_givenNotCurrentPlayer_whenTakingTurn_thenReturn400() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = "00";
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),anyString())).thenThrow(NotCurrentPlayerException.class);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_givenNotCurrentComputerPlayer_whenTakingTurn_thenReturn400() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = null;
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),isNull())).thenThrow(NotCurrentPlayerException.class);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_givenCurrentPlayerNotInGame_whenTakingTurn_thenReturn404() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = "00";
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),anyString())).thenThrow(PlayerNotFoundException.class);

    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_givenCurrentComputerPlayerNotInGame_whenTakingTurn_thenReturn404() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = null;
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),isNull())).thenThrow(PlayerNotFoundException.class);

    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_givenMarkAleadyAdded_whenTakingTurn_thenReturn404() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = "00";
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),anyString())).thenThrow(MarkAlreadySetException.class);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void takeTurn_givenMarkAleadyAddedForComputerPlayer_whenTakingTurn_thenReturn404() throws Exception {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "TestName";
    String squareId = null;
    RequestBuilder request = put("/game/{0}/{1}/{2}",gameId, playerName, squareId);
    when(gameService.takeTurn(anyString(),anyString(),isNull())).thenThrow(MarkAlreadySetException.class);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());
  }
}

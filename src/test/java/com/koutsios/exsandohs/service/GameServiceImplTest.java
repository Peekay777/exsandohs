package com.koutsios.exsandohs.service;

import static com.koutsios.exsandohs.model.MarkType.*;
import static com.koutsios.exsandohs.model.StateType.STARTED;
import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.koutsios.exsandohs.dto.NewGameDto;
import com.koutsios.exsandohs.exception.CreateGameException;
import com.koutsios.exsandohs.exception.GameNotFoundException;
import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.NotCurrentPlayerException;
import com.koutsios.exsandohs.exception.PlayerNotFoundException;
import com.koutsios.exsandohs.model.Board;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.MarkType;
import com.koutsios.exsandohs.model.Square;
import com.koutsios.exsandohs.model.player.ComputerPlayer;
import com.koutsios.exsandohs.model.player.HumanPlayer;
import com.koutsios.exsandohs.model.player.Player;
import com.koutsios.exsandohs.repository.GameRepository;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@MockBeans( {
    @MockBean(ComputerPlayer.class)
})
public class GameServiceImplTest {

  @TestConfiguration
  static class GameServiceImplTestConfiguration {
    @Bean
    public GameServiceImpl gameServiceImpl() {
      return new GameServiceImpl();
    }
  }

  @MockBean
  private GameRepository gameRepository;

  @Autowired
  private GameService subject;

  @Before
  public void init() {
  }

  @Test
  public void createGame_whenCreateGame_thenReturnGame() throws CreateGameException {

    Player ex = HumanPlayer.builder()
        .name("One")
        .build();
    Player oh = new ComputerPlayer();
    NewGameDto newGameDto = NewGameDto.builder()
        .playerEx(ex)
        .playerOh(oh)
        .build();

    Game expected = Game.builder()
        .id(UUID.randomUUID().toString())
        .currentPlayerId(ex.getName())
        .playerEx(ex)
        .playerOh(oh)
        .state(STARTED)
        .board(createBoard())
        .build();
    when(gameRepository.save(any(Game.class))).thenReturn(expected);

    Game actual = subject.createGame(newGameDto);

    assertThat(actual, instanceOf(Game.class));
    assertThat(actual.getPlayerEx(), instanceOf(HumanPlayer.class));
    assertThat(actual.getPlayerOh(), instanceOf(ComputerPlayer.class));
    assertEquals("One", actual.getCurrentPlayerId());
    assertEquals(STARTED, actual.getState());
    assertThat(actual.getBoard().getGameBoard(), instanceOf(Map.class));
  }

  @Test(expected = CreateGameException.class)
  public void createGame_givenPlayersWithSameName_whenCreateGame_thenThrowException()
      throws CreateGameException {

    Player oh = new ComputerPlayer();
    Player ex = HumanPlayer.builder()
        .name(oh.getName())
        .build();
    NewGameDto newGameDto = NewGameDto.builder()
        .playerEx(ex)
        .playerOh(oh)
        .build();

    subject.createGame(newGameDto);

    fail("CreateGameException not thrown");
  }

  @Test
  public void getGame_givenExistingGameId_whenCallingGetGame_thenReturnGame() throws GameNotFoundException {

    String gameId = "00000000-0000-0000-0000-000000000000";
    Game game = Game.builder()
        .id(gameId)
        .build();
    when(gameRepository.findById(anyString())).thenReturn(Optional.of(game));

    Game actual = subject.getGame(gameId);

    assertThat(actual, instanceOf(Game.class));
    assertEquals(gameId, actual.getId());
  }

  @Test(expected = GameNotFoundException.class)
  public void getGame_givenNonExistingGameId_whenCallingGetGame_thenThrowGameNotFoundException() throws GameNotFoundException {

    String gameId = "00000000-0000-0000-0000-000000000000";
    when(gameRepository.findById(anyString())).thenReturn(Optional.empty());

    subject.getGame(gameId);

    fail("GameNotFoundException not thrown");
  }

  @Test
  public void takeTurn_whenCorrectPlayerTurn_thenReturnGameWithCorrectState() throws GameNotFoundException, NotCurrentPlayerException, PlayerNotFoundException, MarkAlreadySetException {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "One";
    String squareId = "00";
    Player ex = HumanPlayer.builder()
        .name(playerName)
        .build();

    Player oh = new ComputerPlayer();
    Game game = Game.builder()
        .id(gameId)
        .currentPlayerId(ex.getName())
        .playerEx(ex)
        .playerOh(oh)
        .state(STARTED)
        .board(createBoard())
        .build();

    Board board = createBoard();
    board.getSquare("00").setMark(X);
    Game expected = Game.builder()
        .id(gameId)
        .currentPlayerId(oh.getName())
        .playerEx(ex)
        .playerOh(oh)
        .state(STARTED)
        .board(board)
        .build();

    when(gameRepository.findById(anyString())).thenReturn(Optional.of(game));
    when(gameRepository.save(any(Game.class))).thenReturn(expected);

    Game actual = subject.takeTurn(gameId, playerName, squareId);

    assertEquals(X, actual.getBoard().getSquare("00").getMark());
    assertEquals(expected.getCurrentPlayerId(), actual.getCurrentPlayerId());
    verify(gameRepository, times(1)).findById(anyString());
    verify(gameRepository, times(1)).save(any(Game.class));
  }

  @Test(expected = GameNotFoundException.class)
  public void takeTurn_givenNonExistingGameId_whenCallingTakeTurn_thenThrowGameNotFoundException() throws GameNotFoundException, MarkAlreadySetException, NotCurrentPlayerException, PlayerNotFoundException {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "One";
    String squareId = "00";
    when(gameRepository.findById(anyString())).thenReturn(Optional.empty());

    subject.takeTurn(gameId, playerName, squareId);

    fail("GameNotFoundException not thrown");
  }

  @Test(expected = PlayerNotFoundException.class)
  public void takeTurn_givenNoPlayersWithCurrentPlayerId_whenCallingTakeTurn_thenThrowPlayerNotFoundException() throws GameNotFoundException, MarkAlreadySetException, NotCurrentPlayerException, PlayerNotFoundException {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String wrongPlayerName = "DoesntExist";
    String squareId = "00";
    Player ex = HumanPlayer.builder()
        .name("One")
        .build();
    Player oh = new ComputerPlayer();
    Game game = Game.builder()
        .id(gameId)
        .currentPlayerId(wrongPlayerName)
        .playerEx(ex)
        .playerOh(oh)
        .state(STARTED)
        .board(createBoard())
        .build();

    when(gameRepository.findById(anyString())).thenReturn(Optional.of(game));

    subject.takeTurn(gameId, wrongPlayerName, squareId);

    fail("PlayerNotFoundException not thrown");
  }

  @Test(expected = NotCurrentPlayerException.class)
  public void takeTurn_givenNotCurrentPlayer_whenCallingTakeTurn_thenThrowNotCurrentPlayerException() throws GameNotFoundException, MarkAlreadySetException, NotCurrentPlayerException, PlayerNotFoundException {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "One";
    String squareId = "00";
    Player ex = HumanPlayer.builder()
        .name(playerName)
        .build();
    Player oh = new ComputerPlayer();
    Game game = Game.builder()
        .id(gameId)
        .currentPlayerId(playerName)
        .playerEx(ex)
        .playerOh(oh)
        .state(STARTED)
        .board(createBoard())
        .build();

    when(gameRepository.findById(anyString())).thenReturn(Optional.of(game));

    subject.takeTurn(gameId, "NotThisPlayer", squareId);

    fail("NotCurrentPlayerException not thrown");
  }

  @Test(expected = MarkAlreadySetException.class)
  public void takeTurn_whenCorrectPlayerTurn_thenThrowMarkAlreadySetException() throws GameNotFoundException, NotCurrentPlayerException, PlayerNotFoundException, MarkAlreadySetException {

    String gameId = "00000000-0000-0000-0000-000000000000";
    String playerName = "One";
    String squareId = "00";
    Player ex = HumanPlayer.builder()
        .name(playerName)
        .build();
    Player oh = new ComputerPlayer();
    Board board = createBoard();
    board.getSquare("00").setMark(X);
    Game game = Game.builder()
        .id(gameId)
        .currentPlayerId(ex.getName())
        .playerEx(ex)
        .playerOh(oh)
        .state(STARTED)
        .board(board)
        .build();
    when(gameRepository.findById(anyString())).thenReturn(Optional.of(game));

    subject.takeTurn(gameId, playerName, squareId);

    fail("MarkAlreadySetException not thrown");;
  }

}

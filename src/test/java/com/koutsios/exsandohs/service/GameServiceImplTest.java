package com.koutsios.exsandohs.service;

import static com.koutsios.exsandohs.model.StateType.IN_PROGRESS;
import static com.koutsios.exsandohs.model.StateType.STARTED;
import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.koutsios.exsandohs.dto.NewGameDto;
import com.koutsios.exsandohs.exception.CreateGameException;
import com.koutsios.exsandohs.exception.GameNotFoundException;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.player.ComputerPlayer;
import com.koutsios.exsandohs.model.player.HumanPlayer;
import com.koutsios.exsandohs.model.player.Player;
import com.koutsios.exsandohs.repository.GameRepository;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

//  @Mock
//  private ComputerPlayer computerPlayer;

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
    assertThat(actual.getBoard(), instanceOf(Map.class));
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

}

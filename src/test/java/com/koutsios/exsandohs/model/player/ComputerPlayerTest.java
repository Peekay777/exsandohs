package com.koutsios.exsandohs.model.player;

import static com.koutsios.exsandohs.model.MarkType.X;
import static com.koutsios.exsandohs.model.TakeTurnKey.GAME;
import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.SquareNotFound;
import com.koutsios.exsandohs.model.Board;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.TakeTurnKey;
import com.koutsios.exsandohs.service.RandomService;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
    @MockBean(Random.class)
})
public class ComputerPlayerTest {

  @TestConfiguration
  static class GameServiceImplTestConfiguration {
    @Bean
    public DumbComputerPlayer computerPlayer() {
      return new DumbComputerPlayer();
    }
  }

  @Autowired
  private Player subject;

  @MockBean
  private RandomService randomService;

  @Test
  public void takeTurn_givenEmptyBoard_whenTakingComputerPlayerTurn_thenAssignMarkToOneSquare() throws MarkAlreadySetException, SquareNotFound {
    Player player = subject;
    player.setMark(X);
    Game game = Game.builder()
        .playerEx(player)
        .board(createBoard())
        .build();
    Map<TakeTurnKey, Object> params = new EnumMap<>(TakeTurnKey.class);
    params.put(GAME, game);
    when(randomService.randomEmptySquareId(any(Game.class))).thenCallRealMethod();
    when(randomService.getRandomInt(anyInt())).thenReturn(0);

    game = player.takeTurn(params);

    final Board board = game.getBoard();
    final List<String> emptySquareIds = board.findEmptySquareIds();
    assertEquals(8, emptySquareIds.size());
    assertEquals(X, board.getSquare("11").getMark());
  }
}
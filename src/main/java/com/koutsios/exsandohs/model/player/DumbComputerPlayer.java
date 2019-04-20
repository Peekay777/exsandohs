package com.koutsios.exsandohs.model.player;

import static com.koutsios.exsandohs.model.TakeTurnKey.GAME;
import static com.koutsios.exsandohs.model.player.PlayerType.DUMB;
import static com.koutsios.exsandohs.util.GameServiceUtils.getParam;

import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.SquareNotFound;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.TakeTurnKey;
import com.koutsios.exsandohs.service.RandomService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DumbComputerPlayer extends Player {

  private static final String NAME = "Dumb AI";
  @Autowired
  private RandomService randomService;

  private DumbComputerPlayer(String name) {
    super(name, DUMB);
  }

  public DumbComputerPlayer() {
    this(NAME);
  }

  @Override
  public Game takeTurn(Map<TakeTurnKey, Object> params) throws MarkAlreadySetException, SquareNotFound {

    Game game = getParam(params, GAME);
    game.getBoard().getSquare(randomService.randomEmptySquareId(game)).setMark(this.getMark());

    return game;
  }
}

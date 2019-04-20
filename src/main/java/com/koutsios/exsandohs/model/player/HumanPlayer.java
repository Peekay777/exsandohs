package com.koutsios.exsandohs.model.player;

import static com.koutsios.exsandohs.model.TakeTurnKey.GAME;
import static com.koutsios.exsandohs.model.TakeTurnKey.SQUARE_ID;
import static com.koutsios.exsandohs.model.player.PlayerType.HUMAN;
import static com.koutsios.exsandohs.util.GameServiceUtils.getParam;

import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.SquareNotFound;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.TakeTurnKey;
import java.util.Map;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class HumanPlayer extends Player {

  HumanPlayer() {
    super("Human Player", HUMAN);
  }

  @Builder
  public HumanPlayer(String name) {
    super(name, HUMAN);
  }

  @Override
  public Game takeTurn(Map<TakeTurnKey, Object> params) throws MarkAlreadySetException, SquareNotFound {

    Game game = getParam(params, GAME);
    String squareId = getParam(params, SQUARE_ID);

    game.getBoard().getSquare(squareId).setMark(this.getMark());

    return game;
  }
}

package com.koutsios.exsandohs.service;

import static com.koutsios.exsandohs.model.StateType.IN_PROGRESS;
import static com.koutsios.exsandohs.model.StateType.STARTED;
import static com.koutsios.exsandohs.model.TakeTurnKey.GAME;
import static com.koutsios.exsandohs.model.TakeTurnKey.SQUARE_ID;
import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static java.util.UUID.randomUUID;

import com.koutsios.exsandohs.dto.NewGameDto;
import com.koutsios.exsandohs.exception.CreateGameException;
import com.koutsios.exsandohs.exception.GameNotFoundException;
import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.NotCurrentPlayerException;
import com.koutsios.exsandohs.exception.SquareNotFound;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.TakeTurnKey;
import com.koutsios.exsandohs.model.player.Player;
import com.koutsios.exsandohs.repository.GameRepository;
import java.util.EnumMap;
import java.util.Map;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

  @Autowired
  private GameRepository gameRepository;

  @Override
  public Game createGame(@NonNull NewGameDto newGameDto) throws CreateGameException {

    if (newGameDto.getPlayerOh().getName().equals(newGameDto.getPlayerEx().getName())) {
      throw new CreateGameException("Player names are the same");
    }

    Game game = Game.builder()
        .id(randomUUID().toString())
        .currentPlayerId(newGameDto.getPlayerEx().getName())
        .playerEx(newGameDto.getPlayerEx())
        .playerOh(newGameDto.getPlayerOh())
        .state(STARTED)
        .board(createBoard())
        .build();

    return gameRepository.save(game);
  }

  @Override
  public Game getGame(@NonNull String gameId) throws GameNotFoundException {
    return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
  }

  @Override
  public Game nextTurn(String gameId,
                       String name,
                       String squareId) throws GameNotFoundException, NotCurrentPlayerException,
      MarkAlreadySetException, SquareNotFound {

    // Find Game
    Game game = gameRepository.findById(gameId)
        .orElseThrow(() -> new GameNotFoundException(gameId));

    // Check its players turn and get current player
    Player player;
    if (game.getCurrentPlayerId().equals(name)) {
      player = game.findCurrentPlayer();
    } else {
      throw new NotCurrentPlayerException(name);
    }

    // Take Turn
    Map<TakeTurnKey, Object> takeTurnMap = new EnumMap<>(TakeTurnKey.class);
    takeTurnMap.put(GAME, game);
    takeTurnMap.put(SQUARE_ID, squareId);
    game = player.takeTurn(takeTurnMap);


    if (game.getBoard().checkForWinner(player.getMark())) {
      // Check for win

    } else if (game.getBoard().checkForDraw()) {
      // Check for Draw

    } else {
      // Next Player Turn and change state to in progress
      game.nextPlayer();
      game.changeState(IN_PROGRESS);
    }

    return gameRepository.save(game);
  }


}

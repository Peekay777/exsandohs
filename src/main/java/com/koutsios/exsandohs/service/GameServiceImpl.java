package com.koutsios.exsandohs.service;

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
import com.koutsios.exsandohs.exception.PlayerNotFoundException;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.TakeTurnKey;
import com.koutsios.exsandohs.model.player.Player;
import com.koutsios.exsandohs.repository.GameRepository;
import java.util.HashMap;
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
  public Game getGame(String gameId) throws GameNotFoundException {
    return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
  }

  @Override
  public Game takeTurn(String gameId, String name, String squareId)
      throws GameNotFoundException, PlayerNotFoundException, NotCurrentPlayerException,
      MarkAlreadySetException {

    Game game = gameRepository.findById(gameId)
        .orElseThrow(() -> new GameNotFoundException(gameId));

    Player player;
    if (game.getCurrentPlayerId().equals(name)) {
      player = game.findCurrentPlayer();
    } else {
      throw new NotCurrentPlayerException(name);
    }

    Map<TakeTurnKey, Object> takeTurnMap = new HashMap<>();
    takeTurnMap.put(GAME, game);
    takeTurnMap.put(SQUARE_ID, squareId);

    game = player.takeTurn(takeTurnMap);


    return gameRepository.save(game);
  }


}

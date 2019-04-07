package com.koutsios.exsandohs.service;

import com.koutsios.exsandohs.dto.NewGameDto;
import com.koutsios.exsandohs.exception.CreateGameException;
import com.koutsios.exsandohs.exception.GameNotFoundException;
import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.NotCurrentPlayerException;
import com.koutsios.exsandohs.exception.PlayerNotFoundException;
import com.koutsios.exsandohs.model.Game;

public interface GameService {

  Game createGame(NewGameDto newGameDto) throws CreateGameException;

  Game getGame(String gameId) throws GameNotFoundException;

  Game takeTurn(String gameId, String name, String squareId) throws GameNotFoundException,
      PlayerNotFoundException, NotCurrentPlayerException, MarkAlreadySetException;
}

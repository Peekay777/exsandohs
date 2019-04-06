package com.koutsios.exsandohs.controllers;

import com.koutsios.exsandohs.dto.NewGameDto;
import com.koutsios.exsandohs.exception.CreateGameException;
import com.koutsios.exsandohs.exception.GameNotFoundException;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

  @Autowired
  private GameService gameService;

  @PostMapping
  public Game createGame(@RequestBody NewGameDto newGameDto) throws CreateGameException {
    return gameService.createGame(newGameDto);
  }

  @GetMapping("/{gameId}")
  public Game getGame(@PathVariable("gameId") String gameId) throws GameNotFoundException {
    return gameService.getGame(gameId);
  }

  @PutMapping({"/{gameId}/{playerName}/{squareId}", "/{gameId}/{playerName}"})
  public Game takeTurn(@PathVariable String gameId,
                       @PathVariable("playerName") String name,
                       @PathVariable(required = false) String squareId) {
    return gameService.takeTurn(gameId, name, squareId);
  }

}

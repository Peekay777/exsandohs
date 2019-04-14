package com.koutsios.exsandohs.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Board {
  private Map<String, Square> gameBoard;

  public Square getSquare(String squareId) {
    return gameBoard.get(squareId);
  }

  public int getSize() {
    return gameBoard.size();
  }
}

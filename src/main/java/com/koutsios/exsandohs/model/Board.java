package com.koutsios.exsandohs.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  /**
   * Finds all the empty squares and returns a list of square Ids.
   * @return List of Square Ids
   */
  public List<String> findEmptySquareIds() {
    return gameBoard.entrySet()
        .stream()
        .filter(entry -> entry.getValue().getMark() == null)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }
}

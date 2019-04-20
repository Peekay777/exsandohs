package com.koutsios.exsandohs.model;

import com.koutsios.exsandohs.exception.SquareNotFound;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Board {
  private Map<String, Square> gameBoard;

  public Square getSquare(String squareId) throws SquareNotFound {
    if (gameBoard.containsKey(squareId)) {
      return gameBoard.get(squareId);
    }
    throw new SquareNotFound(squareId);
  }

  public int getSize() {
    return gameBoard.size();
  }

  /**
   * Finds all the empty squares and returns a list of square Ids.
   *
   * @return List of Square Ids
   */
  public List<String> findEmptySquareIds() {
    return gameBoard.entrySet()
        .stream()
        .filter(entry -> entry.getValue().getMark() == null)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }

  public boolean checkForWinner(MarkType mark) {
    return false;
  }

  public boolean checkForDraw() {
    return false;
  }
}

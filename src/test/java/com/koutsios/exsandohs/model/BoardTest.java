package com.koutsios.exsandohs.model;

import static com.koutsios.exsandohs.model.MarkType.O;
import static com.koutsios.exsandohs.model.MarkType.X;
import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.junit.Assert.assertEquals;

import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import java.util.List;
import org.junit.Test;

public class BoardTest {

  @Test
  public void findEmptySquareIds_givenEmptyBoard_whenFindingIds_thenReturnAllSquareIds() {
    Board board = createBoard();

    List<String> squareIds = board.findEmptySquareIds();

    assertEquals(9, squareIds.size());
  }

  @Test
  public void findEmptySquareIds_givenNonEmptyBoard_whenFindingIds_thenReturnJustUsedSquareIds() throws MarkAlreadySetException {
    Board board = createBoard();

    board.getSquare("00").setMark(X);
    board.getSquare("01").setMark(O);

    List<String> squareIds = board.findEmptySquareIds();

    assertEquals(7, squareIds.size());
  }
}

package com.koutsios.exsandohs.util;

import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.koutsios.exsandohs.exception.SquareNotFound;
import com.koutsios.exsandohs.model.Board;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

public class GameServiceUtilsTest {

  @Test
  public void createBoard_thenReturnNewBoard() throws SquareNotFound {

    Board board = createBoard();

    assertThat(board.getSize(), is(9));
    assertEquals("11", board.getSquare("11").getId());
    assertNull(board.getSquare("11").getMark());
    assertEquals("22", board.getSquare("22").getId());
    assertNull(board.getSquare("22").getMark());
    assertEquals("33", board.getSquare("33").getId());
    assertNull(board.getSquare("33").getMark());
    assertThat(board.getGameBoard(), not(IsMapContaining.hasKey("44")));
  }
}
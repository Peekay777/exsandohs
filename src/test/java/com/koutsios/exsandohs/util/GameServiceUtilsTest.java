package com.koutsios.exsandohs.util;

import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.koutsios.exsandohs.model.Board;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

public class GameServiceUtilsTest {

  @Test
  public void createBoard_thenReturnNewBoard() {

    Board board = createBoard();

    assertThat(board.getSize(), is(9));
    assertEquals("00", board.getSquare("00").getId());
    assertNull(board.getSquare("00").getMark());
    assertEquals("11", board.getSquare("11").getId());
    assertNull(board.getSquare("11").getMark());
    assertEquals("22", board.getSquare("22").getId());
    assertNull(board.getSquare("22").getMark());
    assertThat(board.getGameBoard(), not(IsMapContaining.hasKey("33")));
  }
}
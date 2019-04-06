package com.koutsios.exsandohs.util;

import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.koutsios.exsandohs.model.Square;
import java.util.Map;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

public class GameServiceUtilsTest {

  @Test
  public void createBoard_thenReturnNewBoard() {

    Map<String, Square> board = createBoard();

    assertThat(board.size(), is(9));
    assertEquals("00", board.get("00").getId());
    assertNull(board.get("00").getMark());
    assertEquals("11", board.get("11").getId());
    assertNull(board.get("11").getMark());
    assertEquals("22", board.get("22").getId());
    assertNull(board.get("22").getMark());
    assertThat(board, not(IsMapContaining.hasKey("33")));
  }
}
package com.koutsios.exsandohs.util;

import static lombok.AccessLevel.PRIVATE;

import com.koutsios.exsandohs.model.Square;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class GameServiceUtils {

  /**
   * Creates and empty board.
   * @return An empty board
   */
  public static Map<String, Square> createBoard() {

    Map<String, Square> board = new HashMap<>(9);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        String ivalue = String.valueOf(i);
        String jvalue = String.valueOf(j);
        String key = ivalue + jvalue;
        board.put(key, new Square(key));
      }
    }
    return board;
  }
}

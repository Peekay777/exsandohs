package com.koutsios.exsandohs.util;

import static lombok.AccessLevel.PRIVATE;

import com.koutsios.exsandohs.exception.TurnTypeKeyException;
import com.koutsios.exsandohs.model.Board;
import com.koutsios.exsandohs.model.Square;
import com.koutsios.exsandohs.model.TakeTurnKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class GameServiceUtils {

  private static Random random = new Random();

  /**
   * Creates and empty board.
   *
   * @return An empty board
   */
  public static Board createBoard() {

    Map<String, Square> board = new HashMap<>(9);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        String ivalue = String.valueOf(i);
        String jvalue = String.valueOf(j);
        String key = ivalue + jvalue;
        board.put(key, new Square(key));
      }
    }
    return new Board(board);
  }

  /**
   * Get parameter.
   * @param map Where the parameter is stored
   * @param key Key to find
   * @param <T> Type to return
   * @return Value of parameter
   */
  @SuppressWarnings("unchecked")
  public static <T> T getParam(Map<TakeTurnKey, Object> map, TakeTurnKey key) {

    if (map.containsKey(key)) {
      return (T) map.get(key);
    }

    throw new TurnTypeKeyException("Can not find " + key.toString());
  }
}

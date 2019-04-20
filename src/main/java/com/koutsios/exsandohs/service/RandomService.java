package com.koutsios.exsandohs.service;

import com.koutsios.exsandohs.model.Game;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RandomService {

  @Autowired
  private Random random;

  public int getRandomInt(int max) {
    return random.nextInt(max);
  }

  /**
   * Return random empty square id.
   *
   * @param game Current game object
   * @return Square id
   */
  public String randomEmptySquareId(Game game) {
    final List<String> emptySquareIds = game.getBoard().findEmptySquareIds();
    final int index = getRandomInt(emptySquareIds.size());
    return emptySquareIds.get(index);
  }
}

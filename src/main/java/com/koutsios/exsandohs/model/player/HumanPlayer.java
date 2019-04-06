package com.koutsios.exsandohs.model.player;

import static com.koutsios.exsandohs.model.player.PlayerType.HUMAN;

import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class HumanPlayer extends Player {

  HumanPlayer() {
    super("Human Player", HUMAN);
  }

  @Builder
  public HumanPlayer(String name) {
    super(name, HUMAN);
  }

}

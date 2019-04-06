package com.koutsios.exsandohs.model.player;

import static com.koutsios.exsandohs.model.player.PlayerType.COMPUTER;
import static com.koutsios.exsandohs.util.PlayerUtils.generateName;

import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class ComputerPlayer extends Player {

  private static final String NAMES = "Damon,Dick,Arturo,Dario,Weldon,Trinidad,Albert,Grant,"
      + "Claud,Alphonse,Dannielle,Lucretia,Stephine,Carole,Theresa,Rosanne,Meri,Whitley,Renita,"
      + "Demetra";

  private ComputerPlayer(String name) {
    super(name, COMPUTER);
  }

  public ComputerPlayer() {
    this(generateName(Arrays.asList(NAMES.split(","))));
  }
}

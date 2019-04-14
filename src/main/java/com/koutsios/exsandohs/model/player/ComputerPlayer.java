package com.koutsios.exsandohs.model.player;

import static com.koutsios.exsandohs.model.player.PlayerType.COMPUTER;
import static com.koutsios.exsandohs.util.PlayerUtils.generateName;

import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.TakeTurnKey;
import java.util.Arrays;
import java.util.Map;
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

  @Override
  public Game takeTurn(Map<TakeTurnKey, Object> params) {
    return null;
  }
}

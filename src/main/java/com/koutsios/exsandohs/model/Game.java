package com.koutsios.exsandohs.model;

import com.koutsios.exsandohs.model.player.Player;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {
  @Id
  private String id;
  private String currentPlayerId;
  private StateType state;
  private Player playerEx;
  private Player playerOh;
  private Map<String, Square> board;
}

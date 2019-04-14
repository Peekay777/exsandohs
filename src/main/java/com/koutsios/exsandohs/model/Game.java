package com.koutsios.exsandohs.model;

import com.koutsios.exsandohs.exception.PlayerNotFoundException;
import com.koutsios.exsandohs.model.player.Player;
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
  private Board board;

  /**
   * Gets the player with the required name.
   * @return Player required
   * @throws PlayerNotFoundException Current Player is not playing
   */
  public Player findCurrentPlayer() throws PlayerNotFoundException {

    if (playerEx.getName().equals(currentPlayerId)) {
      return playerEx;
    } else if (playerOh.getName().equals(currentPlayerId)) {
      return playerOh;
    }
    throw new PlayerNotFoundException(currentPlayerId);
  }
}

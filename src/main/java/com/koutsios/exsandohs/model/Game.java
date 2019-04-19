package com.koutsios.exsandohs.model;

import static com.koutsios.exsandohs.model.MarkType.O;
import static com.koutsios.exsandohs.model.MarkType.X;

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

  public void setPlayerEx(Player playerEx) {
    this.playerEx = playerEx;
    if (this.playerEx != null) {
      this.playerEx.setMark(X);
    }
  }

  public void setPlayerOh(Player playerOh) {
    this.playerOh = playerOh;
    if (this.playerOh != null) {
      this.playerOh.setMark(O);
    }
  }

  /**
   * Gets the player with the required name.
   *
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

  /**
   * Change to the next Player.
   */
  public void nextPlayer() {
    if (playerEx.getName().equals(this.currentPlayerId)) {
      this.currentPlayerId = playerOh.getName();
    } else if (playerOh.getName().equals(this.currentPlayerId)) {
      this.currentPlayerId = playerEx.getName();
    }
  }
}

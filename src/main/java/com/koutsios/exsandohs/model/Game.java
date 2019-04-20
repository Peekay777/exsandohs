package com.koutsios.exsandohs.model;

import static com.koutsios.exsandohs.model.MarkType.O;
import static com.koutsios.exsandohs.model.MarkType.X;
import static com.koutsios.exsandohs.model.StateType.IN_PROGRESS;

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
   * Set the X player.
   *
   * @param playerEx Player for X
   */
  public void setPlayerEx(Player playerEx) {
    this.playerEx = playerEx;
    if (this.playerEx != null) {
      this.playerEx.setMark(X);
    }
  }

  /**
   * Set the O player.
   *
   * @param playerOh Player for O
   */
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
   */
  public Player findCurrentPlayer() {
    return playerEx.getName().equals(currentPlayerId) ? playerEx : playerOh;
  }

  /**
   * Change to the next Player.
   */
  public void nextPlayer() {
    this.currentPlayerId = playerEx.getName().equals(this.currentPlayerId) ? playerOh.getName()
        : playerEx.getName();
  }

  /**
   * Change the state of the game
   * @param state The state to change
   */
  public void changeState(StateType state) {
    this.state = state;
  }
}

package com.koutsios.exsandohs.dto;

import static com.koutsios.exsandohs.model.MarkType.O;
import static com.koutsios.exsandohs.model.MarkType.X;

import com.koutsios.exsandohs.model.player.Player;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class NewGameDto {
  @NonNull
  private Player playerEx;
  @NonNull
  private Player playerOh;

  public void setPlayerEx(Player playerEx) {
    this.playerEx = playerEx;
    this.playerEx.setMark(X);
  }

  public void setPlayerOh(Player playerOh) {
    this.playerOh = playerOh;
    this.playerOh.setMark(O);
  }

  /**
   * Generate a new Game.
   *
   * @param playerEx Player with mark X
   * @param playerOh Player with mark O
   */
  @Builder
  public NewGameDto(Player playerEx, Player playerOh) {
    this.playerEx = playerEx;
    this.playerOh = playerOh;

    this.playerEx.setMark(X);
    this.playerOh.setMark(O);
  }
}

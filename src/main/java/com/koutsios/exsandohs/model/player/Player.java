package com.koutsios.exsandohs.model.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import com.koutsios.exsandohs.exception.SquareNotFound;
import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.MarkType;
import com.koutsios.exsandohs.model.TakeTurnKey;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.CUSTOM,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "playerType"
)
@JsonTypeIdResolver(PlayerTypeResolver.class)
@Getter
public abstract class Player {
  private String name;
  private PlayerType playerType;
  @Setter
  private MarkType mark;

  Player(String name, PlayerType playerType) {
    this.name = name;
    this.playerType = playerType;
  }

  public abstract Game takeTurn(Map<TakeTurnKey, Object> params) throws MarkAlreadySetException, SquareNotFound;
}

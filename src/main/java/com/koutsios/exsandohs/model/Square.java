package com.koutsios.exsandohs.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Square {
  private String id;
  private MarkType mark;

  public Square() {
  }

  public Square(String id) {
    this.id = id;
    this.mark = null;
  }
}

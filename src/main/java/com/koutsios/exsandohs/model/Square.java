package com.koutsios.exsandohs.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.koutsios.exsandohs.exception.MarkAlreadySetException;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Square {
  private String id;
  private MarkType mark;

//  public Square() {
//  }

  public Square(String id) {
    this.id = id;
    this.mark = null;
  }

  /**
   * Sets the mark.
   * @param mark Mark Type to set
   * @throws MarkAlreadySetException Once Mark is set can not change value
   */
  public void setMark(MarkType mark) throws MarkAlreadySetException {
    if (this.mark != null) {
      throw new MarkAlreadySetException();
    }
    this.mark = mark;
  }
}

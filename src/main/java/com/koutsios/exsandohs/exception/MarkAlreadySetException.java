package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = BAD_REQUEST)
@Log4j2
public class MarkAlreadySetException extends Exception {

  public MarkAlreadySetException() {
    super();
    log.error("Mark Already Set on Board", this);
  }
}

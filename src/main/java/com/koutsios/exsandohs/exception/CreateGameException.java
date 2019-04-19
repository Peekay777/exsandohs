package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = BAD_REQUEST)
@Log4j2
public class CreateGameException extends Exception {

  public CreateGameException(String message) {
    super(message);
    log.error(message, this);
  }
}

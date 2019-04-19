package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = INTERNAL_SERVER_ERROR)
@Log4j2
public class TurnTypeKeyException extends RuntimeException {

  public TurnTypeKeyException(String message) {
    super(message);
    log.error(message, this);
  }

}

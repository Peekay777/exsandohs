package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.text.MessageFormat;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class GameNotFoundException extends Exception {

  private static final String MESSAGE_PATTERN = "Game Id {0} was not found";

  public GameNotFoundException() {
    super();
  }

  public GameNotFoundException(String gameId) {
    super(MessageFormat.format(MESSAGE_PATTERN, gameId));
  }
}

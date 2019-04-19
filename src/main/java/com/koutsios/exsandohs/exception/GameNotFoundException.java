package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.text.MessageFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
@Log4j2
public class GameNotFoundException extends Exception {

  private static final String MESSAGE_PATTERN = "Game Id {0} was not found";

  public GameNotFoundException(String gameId) {
    super(MessageFormat.format(MESSAGE_PATTERN, gameId));
    log.error(MessageFormat.format(MESSAGE_PATTERN, gameId),this);
  }
}

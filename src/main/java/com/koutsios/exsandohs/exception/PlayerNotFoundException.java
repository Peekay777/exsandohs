package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.text.MessageFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
@Log4j2
public class PlayerNotFoundException extends Exception {

  private static final String MESSAGE_PATTERN = "Player {0} not found";

  public PlayerNotFoundException(String name) {
    super(MessageFormat.format(MESSAGE_PATTERN, name));
    log.error(MessageFormat.format(MESSAGE_PATTERN, name), this);
  }
}

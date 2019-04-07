package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.text.MessageFormat;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class PlayerNotFoundException extends Exception {

  private static final String MESSAGE_PATTERN = "Player {0} not found";

  public PlayerNotFoundException(String name) {
    super(MessageFormat.format(MESSAGE_PATTERN, name));
  }
}

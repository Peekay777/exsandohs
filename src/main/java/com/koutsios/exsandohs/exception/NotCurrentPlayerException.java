package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.text.MessageFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = BAD_REQUEST)
@Log4j2
public class NotCurrentPlayerException extends Exception {

  private static final String MESSAGE_PATTERN = "Current player is not {0}";

  public NotCurrentPlayerException(String name) {
    super(MessageFormat.format(MESSAGE_PATTERN, name));
    log.error(MessageFormat.format(MESSAGE_PATTERN, name), this);
  }
}

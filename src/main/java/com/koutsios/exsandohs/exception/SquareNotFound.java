package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.text.MessageFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = BAD_REQUEST)
@Log4j2
public class SquareNotFound extends Exception {

  private static final String MESSAGE_PATTERN = "The square id {0} not found";

  public SquareNotFound(String squareId) {
    super(MessageFormat.format(MESSAGE_PATTERN, squareId));
    log.error(MessageFormat.format(MESSAGE_PATTERN, squareId), this);
  }
}

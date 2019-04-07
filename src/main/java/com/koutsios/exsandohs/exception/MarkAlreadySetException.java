package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = BAD_REQUEST)
public class MarkAlreadySetException extends Exception {

  public MarkAlreadySetException() {
    super();
  }
}

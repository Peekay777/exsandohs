package com.koutsios.exsandohs.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = INTERNAL_SERVER_ERROR)
public class PlayerTypeResolverException extends RuntimeException {

  public PlayerTypeResolverException() {
    super();
  }

  public PlayerTypeResolverException(String message) {
    super(message);
  }

}

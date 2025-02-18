package com.stgsporting.piehmecup.exceptions;

public class PositionNotFoundException extends NotFoundException {
  public PositionNotFoundException(String message) {
    super(message);
  }

  public PositionNotFoundException() {
    super("Position not found");
  }
}

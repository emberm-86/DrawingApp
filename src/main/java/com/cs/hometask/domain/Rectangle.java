package com.cs.hometask.domain;

import java.util.Map;

public class Rectangle extends Line {

  public Rectangle(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void drawToCanvas(Canvas canvas) {
    canvas.persistPreviousState(createSnapshot(canvas));
    super.drawNewLine(x1, y1, x2, y1, canvas);
    super.drawNewLine(x1, y1, x1, y2, canvas);
    super.drawNewLine(x2, y1, x2, y2, canvas);
    super.drawNewLine(x1, y2, x2, y2, canvas);
  }

  private Map<Coordinate, Character> createSnapshot(Canvas canvas) {
    Map<Coordinate, Character> state = super.createSnapshot(x1, y1, x2, y1, canvas);
    state.putAll(super.createSnapshot(x1, y1, x1, y2, canvas));
    state.putAll(super.createSnapshot(x2, y1, x2, y2, canvas));
    state.putAll(super.createSnapshot(x1, y2, x2, y2, canvas));
    return state;
  }
}

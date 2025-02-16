package com.cs.hometask.domain;

import java.util.HashMap;
import java.util.Map;

public class Line extends Shape {

  public Line(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void drawToCanvas(Canvas canvas) {
    canvas.persistPreviousState(createSnapshot(x1, y1, x2, y2, canvas));
    drawNewLine(x1, y1, x2, y2, canvas);
  }

  protected void drawNewLine(int x1, int y1, int x2, int y2, Canvas canvas) {
    char[][] content = canvas.getContent();
    for (int x = x1; x < x2 + 1; x++) {
      content[y1][x] = 'x';
    }
    for (int y = y1; y < y2 + 1; y++) {
      content[y][x2] = 'x';
    }
  }

  protected Map<Coordinate, Character> createSnapshot(int x1, int y1, int x2, int y2, Canvas canvas) {
    Map<Coordinate, Character> state = new HashMap<>();
    char[][] content = canvas.getContent();

    for (int x = x1; x < x2 + 1; x++) {
      state.put(new Coordinate(y1, x), content[y1][x]);
    }
    for (int y = y1; y < y2 + 1; y++) {
      state.put(new Coordinate(y, x2), content[y][x2]);
    }
    return state;
  }
}

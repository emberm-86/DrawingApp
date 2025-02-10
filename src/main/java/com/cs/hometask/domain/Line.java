package com.cs.hometask.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Line extends Shape {

  public Line(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void draw(Stack<Map<Coordinate, Character>> stateCache, Canvas canvas) {
    stateCache.push(getState(x1, y1, x2, y2, canvas));
    addLine(x1, y1, x2, y2, canvas);
  }

  protected void addLine(int x1, int y1, int x2, int y2, Canvas canvas) {
    for (int x = x1; x < x2 + 1; x++) {
      canvas.getContent()[y1][x] = 'x';
    }
    for (int y = y1; y < y2 + 1; y++) {
      canvas.getContent()[y][x2] = 'x';
    }
  }

  protected Map<Coordinate, Character> getState(int x1, int y1, int x2, int y2, Canvas canvas) {
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

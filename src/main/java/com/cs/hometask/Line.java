package com.cs.hometask;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Line extends Shape {

  public Line(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void draw(Stack<Map<Coord, Character>> stateCache, Canvas canvas) {
    stateCache.push(getState(x1, y1, x2, y2, canvas));
    addLine(x1, y1, x2, y2, canvas);
  }

  protected void addLine(int x1, int y1, int x2, int y2, Canvas canvas) {
    for (int x = x1; x < x2 + 1; x++) {
      canvas.content[y1][x] = 'x';
    }

    for (int y = y1; y < y2 + 1; y++) {
      canvas.content[y][x2] = 'x';
    }
  }

  protected Map<Coord, Character> getState(int x1, int y1, int x2, int y2, Canvas canvas) {
    Map<Coord, Character> state = new HashMap<>();

    for (int x = x1; x < x2 + 1; x++) {
      state.put(new Coord(y1, x), canvas.content[y1][x]);
    }

    for (int y = y1; y < y2 + 1; y++) {
      state.put(new Coord(y, x2), canvas.content[y][x2]);
    }
    return state;
  }
}

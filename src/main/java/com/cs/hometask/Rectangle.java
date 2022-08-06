package com.cs.hometask;

import java.util.Map;
import java.util.Stack;

public class Rectangle extends Line {

  public Rectangle(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void draw(Stack<Map<Coordinate, Character>> stateCache, Canvas canvas) {
    stateCache.push(getState(canvas));
    addRectangle(canvas);
  }

  private void addRectangle(Canvas canvas) {
    super.addLine(x1, y1, x2, y1, canvas);
    super.addLine(x1, y1, x1, y2, canvas);
    super.addLine(x2, y1, x2, y2, canvas);
    super.addLine(x1, y2, x2, y2, canvas);
  }

  private Map<Coordinate, Character> getState(Canvas canvas) {
    Map<Coordinate, Character> state = super.getState(x1, y1, x2, y1, canvas);
    state.putAll(super.getState(x1, y1, x1, y2, canvas));
    state.putAll(super.getState(x2, y1, x2, y2, canvas));
    state.putAll(super.getState(x1, y2, x2, y2, canvas));
    return state;
  }
}

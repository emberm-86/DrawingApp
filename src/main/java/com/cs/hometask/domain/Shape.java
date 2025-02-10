package com.cs.hometask.domain;

import java.util.Map;
import java.util.Stack;

public abstract class Shape {

  int x1;
  int y1;
  int x2;
  int y2;

  public Shape(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    swapCoordinatesIfNeeded();
  }

  private void swapCoordinatesIfNeeded() {
    if (x1 > x2) {
      int c = x1;
      x1 = x2;
      x2 = c;
    }

    if (y1 > y2) {
      int c = y1;
      y1 = y2;
      y2 = c;
    }
  }

  public abstract void draw(Stack<Map<Coordinate, Character>> stateCache, Canvas canvas);

  @Override
  public String toString() {
    return "Input: { x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + " }";
  }
}

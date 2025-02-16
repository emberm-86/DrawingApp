package com.cs.hometask.domain;

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

  public int getX1() {
    return x1;
  }

  public int getY1() {
    return y1;
  }

  public int getX2() {
    return x2;
  }

  public int getY2() {
    return y2;
  }

  @Override
  public String toString() {
    return "Input: { x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + " }";
  }
}

package com.cs.hometask.domain;

public class Line extends Shape {

  public Line(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
  }

  @Override
  public void drawToCanvas(Canvas canvas) {
    draw(canvas);
  }

  protected void draw(Canvas canvas) {
    char[][] content = canvas.getContent();

    for (int x = x1; x < x2 + 1; x++) {
      content[y1][x] = 'x';
    }
    for (int y = y1; y < y2 + 1; y++) {
      content[y][x2] = 'x';
    }
  }
}

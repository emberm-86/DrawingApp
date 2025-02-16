package com.cs.hometask.service;

import com.cs.hometask.domain.Canvas;
import com.cs.hometask.domain.Line;
import com.cs.hometask.domain.Rectangle;
import com.cs.hometask.domain.Shape;

public class ShapeDrawer {

  public static void drawToCanvas(Shape shape, Canvas canvas) {
    switch (shape) {
      case Rectangle rectangle -> rectangle.getLines().forEach(line -> drawLine(line, canvas));
      case Line line -> drawLine(line, canvas);
      default -> {}
    }
  }

  private static void drawLine(Line line, Canvas canvas) {
    char[][] content = canvas.getContent();

    int x1 = line.getX1();
    int x2 = line.getX2();
    int y1 = line.getY1();
    int y2 = line.getY2();

    for (int x = x1; x < x2 + 1; x++) {
      content[y1][x] = 'x';
    }
    for (int y = y1; y < y2 + 1; y++) {
      content[y][x2] = 'x';
    }
  }
}

package com.cs.hometask.domain;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Line {

  private final List<Line> lines = new ArrayList<>();

  public Rectangle(int x1, int y1, int x2, int y2) {
    super(x1, y1, x2, y2);
    lines.add(new Line(x1, y1, x2, y1));
    lines.add(new Line(x1, y1, x1, y2));
    lines.add(new Line(x2, y1, x2, y2));
    lines.add(new Line(x1, y2, x2, y2));
  }

  @Override
  public void drawToCanvas(Canvas canvas) {
    lines.forEach(l -> l.draw(canvas));
  }

  public List<Line> getLines() {
    return lines;
  }
}

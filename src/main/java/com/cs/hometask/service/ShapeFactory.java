package com.cs.hometask.service;

import com.cs.hometask.domain.Line;
import com.cs.hometask.domain.Rectangle;
import com.cs.hometask.domain.Shape;

public class ShapeFactory {

  public static Shape createShape(String typeCode, String[] arguments) {
    int x1 = Integer.parseInt(arguments[1]);
    int y1 = Integer.parseInt(arguments[2]);
    int x2 = Integer.parseInt(arguments[3]);
    int y2 = Integer.parseInt(arguments[4]);

    return switch (typeCode) {
      case "R" -> new Rectangle(x1, y1, x2, y2);
      case "L" -> new Line(x1, y1, x2, y2);
      default -> null;
    };
  }
}

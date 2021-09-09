package com.cs.hometask;

public class ShapeFactory {

  public static Shape createShape(String typeCode, String[] arguments) {
    int x1 = Integer.parseInt(arguments[1]);
    int y1 = Integer.parseInt(arguments[2]);
    int x2 = Integer.parseInt(arguments[3]);
    int y2 = Integer.parseInt(arguments[4]);

    if ("R".equals(typeCode)) {
      return new Rectangle(x1, y1, x2, y2);
    } else if ("L".equals(typeCode)) {
      return new Line(x1, y1, x2, y2);
    }
    return null;
  }
}

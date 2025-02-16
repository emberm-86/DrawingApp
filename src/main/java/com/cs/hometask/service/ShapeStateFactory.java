package com.cs.hometask.service;

import com.cs.hometask.domain.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ShapeStateFactory {

  public static Map<Coordinate, Character> createShapeState(Shape shape, Canvas canvas) {
    if (shape instanceof Rectangle) {
      return createRectSnapshot((Rectangle) shape, canvas);
    } else if (shape instanceof Line) {
      return createLineSnapshot((Line) shape, canvas);
    }
    return new HashMap<>();
  }

  private static Map<Coordinate, Character> createRectSnapshot(Rectangle rectangle, Canvas canvas) {
    return rectangle.getLines().stream()
        .map(line -> createLineSnapshot(line, canvas))
        .flatMap(map -> map.entrySet().stream())
        .collect(
            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
  }

  private static Map<Coordinate, Character> createLineSnapshot(Line line, Canvas canvas) {
    Map<Coordinate, Character> state = new HashMap<>();
    char[][] content = canvas.getContent();

    int x1 = line.getX1();
    int x2 = line.getX2();
    int y1 = line.getY1();
    int y2 = line.getY2();

    for (int x = x1; x < x2 + 1; x++) {
      state.put(new Coordinate(y1, x), content[y1][x]);
    }
    for (int y = y1; y < y2 + 1; y++) {
      state.put(new Coordinate(y, x2), content[y][x2]);
    }
    return state;
  }
}

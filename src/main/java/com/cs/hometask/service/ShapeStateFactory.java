package com.cs.hometask.service;

import com.cs.hometask.domain.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ShapeStateFactory {

  public static Map<Coordinate, Character> createSnapShot(Shape shape, Canvas canvas) {
    return switch (shape) {
      case Rectangle rectangle -> createSnapshot(rectangle, canvas);
      case Line line -> createSnapshot(line, canvas);
      default -> new HashMap<>();
    };
  }

  private static Map<Coordinate, Character> createSnapshot(Rectangle rectangle, Canvas canvas) {
    return rectangle.getLines().stream()
        .map(line -> createSnapshot(line, canvas))
        .flatMap(map -> map.entrySet().stream())
        .collect(
            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
  }

  private static Map<Coordinate, Character> createSnapshot(Line line, Canvas canvas) {
    Map<Coordinate, Character> stateBeforeDrawing = new HashMap<>();
    char[][] content = canvas.getContent();

    int x1 = line.getX1();
    int x2 = line.getX2();
    int y1 = line.getY1();
    int y2 = line.getY2();

    for (int x = x1; x < x2 + 1; x++) {
      stateBeforeDrawing.put(new Coordinate(y1, x), content[y1][x]);
    }
    for (int y = y1; y < y2 + 1; y++) {
      stateBeforeDrawing.put(new Coordinate(y, x2), content[y][x2]);
    }
    return stateBeforeDrawing;
  }
}

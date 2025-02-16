package com.cs.hometask.domain;

import com.cs.hometask.service.ShapeStateFactory;

import java.util.*;

public class Canvas {

  public static final Integer CANVAS_FRAME_SIZE = 1;

  private final char[][] content;
  private final char td;
  private final char lr;
  private final Stack<Map<Coordinate, Character>> stateCache;

  public Canvas(char td, char lr, int w, int h) {
    if (w < 1 || h < 1) {
      throw new IllegalArgumentException(
          "Error: Not valid dimensions for the canvas, width: " + w + " height: " + h);
    }

    content = new char[h + 2 * CANVAS_FRAME_SIZE][w + 2 * CANVAS_FRAME_SIZE];

    this.td = td;
    this.lr = lr;
    this.stateCache = new Stack<>();

    reset();
  }

  public void reset() {
    int h = content.length;

    for (int i = 0; i < h; i++) {
      int w = content[i].length;

      for (int j = 0; j < w; j++) {

        if (i == 0 || i == h - 1) {
          content[i][j] = td;
        } else if (j > 0 && j < w - 1) {
          content[i][j] = ' ';
        } else {
          content[i][j] = lr;
        }
      }
    }
    stateCache.clear();
  }

  private boolean isValidX(int x) {
    return x >= CANVAS_FRAME_SIZE && x < content[0].length - CANVAS_FRAME_SIZE;
  }

  private boolean isValidY(int y) {
    return y >= CANVAS_FRAME_SIZE && y < content.length - CANVAS_FRAME_SIZE;
  }

  private boolean isValid(Shape d) {
    return isValidX(d.x1) && isValidX(d.x2) && isValidY(d.y1) && isValidY(d.y2);
  }

  public char[][] getContent() {
    return content;
  }

  public void addShape(Shape shape) {
    if (!isValid(shape)) {
      throw new IllegalArgumentException("Error: Your input is out of the canvas! " + shape);
    }
    persistPreviousState(ShapeStateFactory.createShapeState(shape, this));
    shape.drawToCanvas(this);
  }

  public void bucketFill(int x, int y, char c) {
    if (!isValidX(x) || !isValidY(y)) {
      throw new IllegalArgumentException(
          "Error: Your input is out of the canvas size! Input: { x=" + x + ", y=" + y + " }");
    }

    if (content[y][x] != ' ') {
      return;
    }

    Map<Coordinate, Character> beforeState = new HashMap<>();

    boolean[][] vis = new boolean[content.length][content[0].length];

    Queue<Coordinate> queue = new LinkedList<>();

    queue.add(new Coordinate(y, x));

    while (!queue.isEmpty()) {
      Coordinate coordinate = queue.peek();

      int x1 = coordinate.getX();
      int y1 = coordinate.getY();

      char preColor = content[y1][x1];
      beforeState.put(new Coordinate(y1, x1), preColor);
      content[y1][x1] = c;

      queue.remove();

      if (isValidX(x1 + 1) && !vis[y1][x1 + 1] && content[y1][x1 + 1] == preColor) {
        addNeighbourToQueue(y1, x1 + 1, preColor, vis, beforeState, queue);
      }

      if (isValidX(x1 - 1) && !vis[y1][x1 - 1] && content[y1][x1 - 1] == preColor) {
        addNeighbourToQueue(y1, x1 - 1, preColor, vis, beforeState, queue);
      }

      if (isValidY(y1 + 1) && !vis[y1 + 1][x1] && content[y1 + 1][x1] == preColor) {
        addNeighbourToQueue(y1 + 1, x1, preColor, vis, beforeState, queue);
      }

      if (isValidY(y1 - 1) && !vis[y1 - 1][x1] && content[y1 - 1][x1] == preColor) {
        addNeighbourToQueue(y1 - 1, x1, preColor, vis, beforeState, queue);
      }
    }
    stateCache.push(beforeState);
  }

  public void undoChange() {
    if (!stateCache.isEmpty()) {
      Map<Coordinate, Character> lastChange = stateCache.pop();
      lastChange.forEach((k, v) -> content[k.getY()][k.getX()] = v);
    }
  }

  private void addNeighbourToQueue(
      int ny,
      int nx,
      char preColor,
      boolean[][] vis,
      Map<Coordinate, Character> beforeState,
      Queue<Coordinate> queue) {

    Coordinate coordinate = new Coordinate(ny, nx);
    beforeState.put(coordinate, preColor);
    queue.add(coordinate);
    vis[ny][nx] = true;
  }

  public void draw() {
    System.out.println("Canvas state after the last successful drawing:");
    Arrays.stream(content).forEach(line -> System.out.println(String.valueOf(line)));
  }

  public void persistPreviousState(Map<Coordinate, Character> state) {
    stateCache.push(state);
  }
}

package com.cs.hometask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import static com.cs.hometask.Canvas.CANVAS_FRAME_SIZE;

public class DrawingServiceImpl implements DrawingService {

  private final Canvas canvas;
  private final Stack<Map<Coordinate, Character>> stateCache;

  public DrawingServiceImpl(Canvas canvas) {
    this.canvas = canvas;
    this.stateCache = new Stack<>();
  }

  @Override
  public void addShape(Shape shape) {
    if (!isValid(shape)) {
      throw new IllegalArgumentException("Error: Your input is out of the canvas! " + shape);
    }
    shape.draw(stateCache, canvas);
  }

  @Override
  public void bucketFill(int x, int y, char c) {
    if (!isValidX(x) || !isValidY(y)) {
      throw new IllegalArgumentException(
          "Error: Your input is out of the canvas size! Input: { x=" + x + ", y=" + y + " }");
    }

    if (canvas.content[y][x] != ' ') {
      return;
    }

    Map<Coordinate, Character> beforeState = new HashMap<>();

    boolean[][] vis = new boolean[canvas.content.length][canvas.content[0].length];

    Queue<Coordinate> queue = new LinkedList<>();

    queue.add(new Coordinate(y, x));

    while (!queue.isEmpty()) {
      Coordinate coordinate = queue.peek();

      int x1 = coordinate.x;
      int y1 = coordinate.y;

      char preColor = canvas.content[y1][x1];
      beforeState.put(new Coordinate(y1, x1), preColor);
      canvas.content[y1][x1] = c;

      queue.remove();

      if (isValidX(x1 + 1) && !vis[y1][x1 + 1] && canvas.content[y1][x1 + 1] == preColor) {
        addNeighbourToQueue(y1, x1 + 1, preColor, vis, beforeState, queue);
      }

      if (isValidX(x1 - 1) && !vis[y1][x1 - 1] && canvas.content[y1][x1 - 1] == preColor) {
        addNeighbourToQueue(y1, x1 - 1, preColor, vis, beforeState, queue);
      }

      if (isValidY(y1 + 1) && !vis[y1 + 1][x1] && canvas.content[y1 + 1][x1] == preColor) {
        addNeighbourToQueue(y1 + 1, x1, preColor, vis, beforeState, queue);
      }

      if (isValidY(y1 - 1) && !vis[y1 - 1][x1] && canvas.content[y1 - 1][x1] == preColor) {
        addNeighbourToQueue(y1 - 1, x1, preColor, vis, beforeState, queue);
      }
    }
    stateCache.push(beforeState);
  }

  private void addNeighbourToQueue(int ny, int nx, char preColor, boolean[][] vis,
      Map<Coordinate, Character> beforeState, Queue<Coordinate> queue) {
    Coordinate coordinate = new Coordinate(ny, nx);
    beforeState.put(coordinate, preColor);
    queue.add(coordinate);
    vis[ny][nx] = true;
  }

  @Override
  public void clearCanvas() {
    canvas.reset();
    stateCache.clear();
  }

  @Override
  public void undoChange() {
    if (!stateCache.isEmpty()) {
      Map<Coordinate, Character> lastChange = stateCache.pop();
      lastChange.forEach((k, v) -> canvas.content[k.y][k.x] = v);
    }
  }

  @Override
  public void draw() {
    System.out.println("Canvas state after the last successful drawing:");
    Arrays.stream(canvas.content).forEach(line -> System.out.println(String.valueOf(line)));
  }

  private boolean isValid(Shape d) {
    return isValidX(d.x1) && isValidX(d.x2) && isValidY(d.y1) && isValidY(d.y2);
  }

  private boolean isValidX(int x) {
    return x >= CANVAS_FRAME_SIZE && x < canvas.content[0].length - CANVAS_FRAME_SIZE;
  }

  private boolean isValidY(int y) {
    return y >= CANVAS_FRAME_SIZE && y < canvas.content.length - CANVAS_FRAME_SIZE;
  }
}
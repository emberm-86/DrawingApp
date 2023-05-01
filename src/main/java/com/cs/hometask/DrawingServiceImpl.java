package com.cs.hometask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class DrawingServiceImpl implements DrawingService {

  private Canvas canvas;
  private final Stack<Map<Coordinate, Character>> stateCache;

  public DrawingServiceImpl() {
    this.stateCache = new Stack<>();
  }

  @Override
  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  @Override
  public void addShape(Shape shape) {
    if (!isCanvasCreated()) {
      System.out.println("The canvas has not been created!");
      return;
    }

    if (!canvas.isValid(shape)) {
      throw new IllegalArgumentException("Error: Your input is out of the canvas! " + shape);
    }

    shape.draw(stateCache, canvas);
  }

  @Override
  public void bucketFill(int x, int y, char c) {
    if (!isCanvasCreated()) {
      System.out.println("The canvas has not been created!");
      return;
    }

    if (!canvas.isValidX(x) || !canvas.isValidY(y)) {
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

      if (canvas.isValidX(x1 + 1) && !vis[y1][x1 + 1] && canvas.content[y1][x1 + 1] == preColor) {
        addNeighbourToQueue(y1, x1 + 1, preColor, vis, beforeState, queue);
      }

      if (canvas.isValidX(x1 - 1) && !vis[y1][x1 - 1] && canvas.content[y1][x1 - 1] == preColor) {
        addNeighbourToQueue(y1, x1 - 1, preColor, vis, beforeState, queue);
      }

      if (canvas.isValidY(y1 + 1) && !vis[y1 + 1][x1] && canvas.content[y1 + 1][x1] == preColor) {
        addNeighbourToQueue(y1 + 1, x1, preColor, vis, beforeState, queue);
      }

      if (canvas.isValidY(y1 - 1) && !vis[y1 - 1][x1] && canvas.content[y1 - 1][x1] == preColor) {
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
    if (!isCanvasCreated()) {
      return;
    }
    System.out.println("Canvas state after the last successful drawing:");
    Arrays.stream(canvas.content).forEach(line -> System.out.println(String.valueOf(line)));
  }

  @Override
  public boolean isCanvasCreated() {
    return canvas != null;
  }
}
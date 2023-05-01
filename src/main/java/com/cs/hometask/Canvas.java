package com.cs.hometask;

public class Canvas {

  static final Integer CANVAS_FRAME_SIZE = 1;
  final char[][] content;
  private final char td;
  private final char lr;

  public Canvas(char td, char lr, int w, int h) {
    if (w < 1 || h < 1) {
      throw new IllegalArgumentException(
          "Error: Not valid dimensions for the canvas, width: " + w + " height: " + h);
    }

    content = new char[h + 2 * CANVAS_FRAME_SIZE][w + 2 * CANVAS_FRAME_SIZE];

    this.td = td;
    this.lr = lr;

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
  }

  public boolean isValidX(int x) {
    return x >= CANVAS_FRAME_SIZE && x < content[0].length - CANVAS_FRAME_SIZE;
  }

  public boolean isValidY(int y) {
    return y >= CANVAS_FRAME_SIZE && y < content.length - CANVAS_FRAME_SIZE;
  }

  public boolean isValid(Shape d) {
    return isValidX(d.x1) && isValidX(d.x2) && isValidY(d.y1) && isValidY(d.y2);
  }
}

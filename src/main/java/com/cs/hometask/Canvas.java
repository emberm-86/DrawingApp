package com.cs.hometask;

public class Canvas {

  public static final Integer CANVAS_FRAME_SIZE = 1;
  public final char[][] content;
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
}

package com.cs.hometask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Console drawing application test.
 * <p>
 * This test is more independent of the parameters like the canvas size or possible values of drawn
 * char, filled char.
 *
 * @author Matyas Ember
 */
public class DrawingServiceTest {

  private static final char TOP_DOWN_BOUNDARY_CHAR = '-';
  private static final char LEFT_RIGHT_BOUNDARY_CHAR = '|';
  private static final char DRAWN_CHAR = 'x';
  private static final char FILLING_CHAR = 'o';
  private static final Integer WIDTH = 20;
  private static final Integer HEIGHT = 4;

  private final Canvas canvas = new Canvas(TOP_DOWN_BOUNDARY_CHAR, LEFT_RIGHT_BOUNDARY_CHAR, WIDTH,
      HEIGHT);
  private final DrawingService drawingService = new DrawingServiceImpl(canvas);

  @AfterEach
  public void cleanUp() {
    drawingService.clearCanvas();
  }

  @Test
  public void testCreateCanvasWithInvalidSize() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Canvas(TOP_DOWN_BOUNDARY_CHAR, LEFT_RIGHT_BOUNDARY_CHAR, 0, 0));
  }

  @Test
  public void testAddHorizontalLine() {
    int x1 = 1;
    int y = 2;
    int x2 = 6;

    drawingService.addShape(new Line(x1, y, x2, y));

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < y; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    String line = String.valueOf(canvas.content[y]);
    Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
    Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
    Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);

    for (int i = y + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testAddVerticalLine() {
    int x = 6;
    int y1 = 3;
    int y2 = 4;
    drawingService.addShape(new Line(x, y1, x, y2));

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    for (int i = y1; i < y2; i++) {
      String line = String.valueOf(canvas.content[i]);
      Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 1);
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testAddRectangle() {
    int x1 = 14;
    int y1 = 1;
    int x2 = 18;
    int y2 = 3;
    drawingService.addShape(new Rectangle(x1, y1, x2, y2));

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > y1 && i < y2) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testAddBucketFillInsideRectangle() {
    int x1 = 14;
    int y1 = 1;
    int x2 = 18;
    int y2 = 3;

    int bx1 = 15;
    int by1 = 2;

    drawingService.addShape(new Rectangle(x1, y1, x2, y2));
    drawingService.bucketFill(bx1, by1, FILLING_CHAR);

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > y1 && i < y2) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
        Assertions.assertEquals(getNumberOfFillingCharsInALine(line), x2 - x1 - 1);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
        Assertions.assertEquals(getNumberOfFillingCharsInALine(line), 0);
      }
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testAddBucketFillOutsideRectangle() {
    int x1 = 14;
    int y1 = 1;
    int x2 = 18;
    int y2 = 3;

    int bx1 = 10;
    int by1 = 3;

    drawingService.addShape(new Rectangle(x1, y1, x2, y2));
    drawingService.bucketFill(bx1, by1, FILLING_CHAR);

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getNumberOfFillingCharsInALine(String.valueOf(canvas.content[i])),
          WIDTH);
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > y1 && i < y2) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      Assertions.assertEquals(getNumberOfFillingCharsInALine(line), WIDTH - x2 + x1 - 1);
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getNumberOfFillingCharsInALine(String.valueOf(canvas.content[i])),
          WIDTH);
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testAddRectangleReversedXandYInput() {
    int x1 = 14;
    int y1 = 1;
    int x2 = 18;
    int y2 = 3;

    drawingService.addShape(new Rectangle(x2, y2, x1, y1));

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > y1 && i < y2) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testDrawAtTheCanvasBorders() {
    drawingService.addShape(new Rectangle(1, 1, WIDTH, HEIGHT));

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < HEIGHT + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > 1 && i < HEIGHT) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), WIDTH);
      }
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), 1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), WIDTH);
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testDrawOutOfTheCanvasAddShape() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(0, 1, WIDTH, 1)));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, WIDTH + 1, 1)));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 0, 1, HEIGHT)));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, 1, HEIGHT + 1)));
  }

  @Test
  public void testDrawOutOfTheCanvasBucketFill() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(0, 1, 'o'));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(WIDTH + 1, 1, 'o'));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(1, 0, 'o'));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(1, HEIGHT + 1, 'o'));
  }

  @Test
  public void testDrawOnACompletelyBucketFilledCanvas() {
    int x1 = 14;
    int y1 = 1;
    int x2 = 18;
    int y2 = 3;

    int bx1 = 10;
    int by1 = 3;

    drawingService.bucketFill(bx1, by1, FILLING_CHAR);
    drawingService.addShape(new Rectangle(x1, y1, x2, y2));

    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[0]));

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getNumberOfFillingCharsInALine(String.valueOf(canvas.content[i])),
          WIDTH);
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > y1 && i < y2) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
        Assertions.assertEquals(getNumberOfFillingCharsInALine(line), WIDTH - 2);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
        Assertions.assertEquals(getNumberOfFillingCharsInALine(line), WIDTH - x2 + x1 - 1);
      }
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getNumberOfFillingCharsInALine(String.valueOf(canvas.content[i])),
          WIDTH);
    }
    Assertions.assertEquals(getTopDownBoundaryLine(), String.valueOf(canvas.content[HEIGHT + 1]));
  }

  @Test
  public void testUndoChanges() {
    int x1 = 1;
    int y1 = 2;
    int x2 = 6;

    drawingService.addShape(new Line(x1, y1, x2, y1));
    drawingService.undoChange();
    Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[y1]));

    x1 = 6;
    y1 = 3;
    int y2 = 4;
    drawingService.addShape(new Line(x1, y1, x1, y2));
    drawingService.undoChange();
    for (int i = y1; i < y2 + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    x1 = 14;
    y1 = 1;
    x2 = 18;
    y2 = 3;

    drawingService.addShape(new Rectangle(x1, y1, x2, y2));
    drawingService.undoChange();
    for (int i = y1; i < y2 + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    int bx1 = 10;
    int by1 = 3;

    drawingService.addShape(new Rectangle(x1, y1, x2, y2));
    drawingService.bucketFill(bx1, by1, FILLING_CHAR);
    drawingService.undoChange();

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > y1 && i < y2) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    bx1 = 15;
    by1 = 2;
    drawingService.bucketFill(bx1, by1, FILLING_CHAR);
    drawingService.undoChange();

    for (int i = 1; i < y1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(canvas.content[i]);
      if (i > y1 && i < y2) {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        Assertions.assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      Assertions.assertEquals(line.indexOf(DRAWN_CHAR), x1);
      Assertions.assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      Assertions.assertEquals(getEmptyInnerLine(), String.valueOf(canvas.content[i]));
    }
  }

  public String getTopDownBoundaryLine() {
    return ("" + TOP_DOWN_BOUNDARY_CHAR).repeat(WIDTH + 2 * Canvas.CANVAS_FRAME_SIZE);
  }

  public String getEmptyInnerLine() {
    return LEFT_RIGHT_BOUNDARY_CHAR + " ".repeat(WIDTH) + LEFT_RIGHT_BOUNDARY_CHAR;
  }

  private int getNumberOfDrawnCharsInALine(String line) {
    return (int) line.chars().filter(ch -> ch == DRAWN_CHAR).count();
  }

  private int getNumberOfFillingCharsInALine(String line) {
    return (int) line.chars().filter(ch -> ch == FILLING_CHAR).count();
  }
}

package com.cs.hometask;

import com.cs.hometask.domain.Canvas;
import com.cs.hometask.domain.Line;
import com.cs.hometask.domain.Rectangle;
import com.cs.hometask.service.DrawingService;
import com.cs.hometask.service.DrawingServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.cs.hometask.domain.Canvas.CANVAS_FRAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

  private static final Canvas CANVAS = new Canvas(TOP_DOWN_BOUNDARY_CHAR, LEFT_RIGHT_BOUNDARY_CHAR,
      WIDTH, HEIGHT);
  private static char[][] content;
  private static final DrawingService drawingService = new DrawingServiceImpl();

  @BeforeAll
  public static void setUp() {
    drawingService.setCanvas(CANVAS);
    content = CANVAS.getContent();
  }

  @AfterEach
  public void cleanUp() {
    drawingService.clearCanvas();
  }

  @Test
  public void testCreateCanvasWithInvalidSize() {
    assertThrows(IllegalArgumentException.class,
        () -> new Canvas(TOP_DOWN_BOUNDARY_CHAR, LEFT_RIGHT_BOUNDARY_CHAR, 0, 0));
  }

  @Test
  public void testAddHorizontalLine() {
    int x1 = 1;
    int y = 2;
    int x2 = 6;

    drawingService.addShape(new Line(x1, y, x2, y));

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < y; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    String line = String.valueOf(content[y]);

    assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
    assertEquals(line.indexOf(DRAWN_CHAR), x1);
    assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);

    for (int i = y + 1; i < HEIGHT + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
  }

  @Test
  public void testAddVerticalLine() {
    int x = 6;
    int y1 = 3;
    int y2 = 4;

    drawingService.addShape(new Line(x, y1, x, y2));

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < y1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    for (int i = y1; i < y2; i++) {
      String line = String.valueOf(content[i]);
      assertEquals(getNumberOfDrawnCharsInALine(line), 1);
      assertEquals(line.indexOf(DRAWN_CHAR), x);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
  }

  @Test
  public void testAddRectangle() {
    int x1 = 14;
    int y1 = 1;
    int x2 = 18;
    int y2 = 3;

    drawingService.addShape(new Rectangle(x1, y1, x2, y2));

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < y1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > y1 && i < y2) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      assertEquals(line.indexOf(DRAWN_CHAR), x1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
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

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < y1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > y1 && i < y2) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
        assertEquals(getNumberOfFillingCharsInALine(line), x2 - x1 - 1);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
        assertEquals(getNumberOfFillingCharsInALine(line), 0);
      }
      assertEquals(line.indexOf(DRAWN_CHAR), x1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
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

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < y1; i++) {
      assertEquals(getNumberOfFillingCharsInALine(String.valueOf(content[i])), WIDTH);
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > y1 && i < y2) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      assertEquals(getNumberOfFillingCharsInALine(line), WIDTH - x2 + x1 - 1);
      assertEquals(line.indexOf(DRAWN_CHAR), x1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getNumberOfFillingCharsInALine(String.valueOf(content[i])), WIDTH);
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
  }

  @Test
  public void testAddRectangleReversedXAndYInput() {
    int x1 = 14;
    int y1 = 1;
    int x2 = 18;
    int y2 = 3;

    drawingService.addShape(new Rectangle(x2, y2, x1, y1));

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < y1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > y1 && i < y2) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      assertEquals(line.indexOf(DRAWN_CHAR), x1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
  }

  @Test
  public void testDrawAtTheCanvasBorders() {
    drawingService.addShape(new Rectangle(1, 1, WIDTH, HEIGHT));

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < HEIGHT + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > 1 && i < HEIGHT) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), WIDTH);
      }
      assertEquals(line.indexOf(DRAWN_CHAR), 1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), WIDTH);
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
  }

  @Test
  public void testDrawOutOfTheCanvasAddShape() {
    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(0, 1, WIDTH, 1)));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, WIDTH + 1, 1)));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 0, 1, HEIGHT)));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, 1, HEIGHT + 1)));
  }

  @Test
  public void testDrawOutOfTheCanvasBucketFill() {
    assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(0, 1, 'o'));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(WIDTH + 1, 1, 'o'));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(1, 0, 'o'));

    assertThrows(IllegalArgumentException.class,
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

    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[0]));

    for (int i = 1; i < y1; i++) {
      assertEquals(getNumberOfFillingCharsInALine(String.valueOf(content[i])), WIDTH);
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > y1 && i < y2) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
        assertEquals(getNumberOfFillingCharsInALine(line), WIDTH - 2);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
        assertEquals(getNumberOfFillingCharsInALine(line), WIDTH - x2 + x1 - 1);
      }
      assertEquals(line.indexOf(DRAWN_CHAR), x1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getNumberOfFillingCharsInALine(String.valueOf(content[i])), WIDTH);
    }
    assertEquals(getTopDownBoundaryLine(), String.valueOf(content[HEIGHT + 1]));
  }

  @Test
  public void testUndoChanges() {
    int x1 = 1;
    int y1 = 2;
    int x2 = 6;

    drawingService.addShape(new Line(x1, y1, x2, y1));
    drawingService.undoChange();

    assertEquals(getEmptyInnerLine(), String.valueOf(content[y1]));

    x1 = 6;
    y1 = 3;
    int y2 = 4;

    drawingService.addShape(new Line(x1, y1, x1, y2));
    drawingService.undoChange();

    for (int i = y1; i < y2 + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    x1 = 14;
    y1 = 1;
    x2 = 18;
    y2 = 3;

    drawingService.addShape(new Rectangle(x1, y1, x2, y2));
    drawingService.undoChange();

    for (int i = y1; i < y2 + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    int bx1 = 10;
    int by1 = 3;

    drawingService.addShape(new Rectangle(x1, y1, x2, y2));
    drawingService.bucketFill(bx1, by1, FILLING_CHAR);
    drawingService.undoChange();

    for (int i = 1; i < y1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > y1 && i < y2) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      assertEquals(line.indexOf(DRAWN_CHAR), x1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    bx1 = 15;
    by1 = 2;
    drawingService.bucketFill(bx1, by1, FILLING_CHAR);
    drawingService.undoChange();

    for (int i = 1; i < y1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }

    for (int i = y1; i < y2 + 1; i++) {
      String line = String.valueOf(content[i]);
      if (i > y1 && i < y2) {
        assertEquals(getNumberOfDrawnCharsInALine(line), 2);
      } else {
        assertEquals(getNumberOfDrawnCharsInALine(line), x2 - x1 + 1);
      }
      assertEquals(line.indexOf(DRAWN_CHAR), x1);
      assertEquals(line.lastIndexOf(DRAWN_CHAR), x2);
    }

    for (int i = y2 + 1; i < HEIGHT + 1; i++) {
      assertEquals(getEmptyInnerLine(), String.valueOf(content[i]));
    }
  }

  public String getTopDownBoundaryLine() {
    return ("" + TOP_DOWN_BOUNDARY_CHAR).repeat(WIDTH + 2 * CANVAS_FRAME_SIZE);
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

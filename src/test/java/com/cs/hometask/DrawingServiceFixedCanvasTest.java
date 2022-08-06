package com.cs.hometask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Console drawing application test.
 * <p>
 * Note: if you change the size of the canvas or the boundary characters this test will break. I
 * just kept it because it is self-explaining regarding the task description.
 *
 * @author Matyas Ember
 */
public class DrawingServiceFixedCanvasTest {

  private final Canvas canvas = new Canvas('-', '|', 20, 4);
  private final DrawingService drawingService = new DrawingServiceImpl(canvas);

  @AfterEach
  public void cleanUp() {
    drawingService.clearCanvas();
  }

  @Test
  public void testAddLine() {
    drawingService.addShape(new Line(1, 2, 6, 2));

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|                    |", valueOf(canvas.content[1]));
    assertEquals("|xxxxxx              |", valueOf(canvas.content[2]));
    assertEquals("|                    |", valueOf(canvas.content[3]));
    assertEquals("|                    |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));

    drawingService.addShape(new Line(6, 3, 6, 4));

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|                    |", valueOf(canvas.content[1]));
    assertEquals("|xxxxxx              |", valueOf(canvas.content[2]));
    assertEquals("|     x              |", valueOf(canvas.content[3]));
    assertEquals("|     x              |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));
  }

  @Test
  public void testAddRectangle() {
    drawingService.addShape(new Line(1, 2, 6, 2));
    drawingService.addShape(new Line(6, 3, 6, 4));
    drawingService.addShape(new Rectangle(14, 1, 18, 3));

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|             xxxxx  |", valueOf(canvas.content[1]));
    assertEquals("|xxxxxx       x   x  |", valueOf(canvas.content[2]));
    assertEquals("|     x       xxxxx  |", valueOf(canvas.content[3]));
    assertEquals("|     x              |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));
  }

  @Test
  public void testAddBucketFill() {
    drawingService.addShape(new Line(1, 2, 6, 2));
    drawingService.addShape(new Line(6, 3, 6, 4));
    drawingService.addShape(new Rectangle(14, 1, 18, 3));
    drawingService.bucketFill(10, 3, 'o');

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|oooooooooooooxxxxxoo|", valueOf(canvas.content[1]));
    assertEquals("|xxxxxxooooooox   xoo|", valueOf(canvas.content[2]));
    assertEquals("|     xoooooooxxxxxoo|", valueOf(canvas.content[3]));
    assertEquals("|     xoooooooooooooo|", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));

    drawingService.bucketFill(15, 2, 'o');

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|oooooooooooooxxxxxoo|", valueOf(canvas.content[1]));
    assertEquals("|xxxxxxoooooooxoooxoo|", valueOf(canvas.content[2]));
    assertEquals("|     xoooooooxxxxxoo|", valueOf(canvas.content[3]));
    assertEquals("|     xoooooooooooooo|", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));
  }

  @Test
  public void testAddRectangleReversedXAndYInput() {
    drawingService.addShape(new Line(6, 2, 1, 2));
    drawingService.addShape(new Line(6, 4, 6, 3));
    drawingService.addShape(new Rectangle(18, 3, 14, 1));

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|             xxxxx  |", valueOf(canvas.content[1]));
    assertEquals("|xxxxxx       x   x  |", valueOf(canvas.content[2]));
    assertEquals("|     x       xxxxx  |", valueOf(canvas.content[3]));
    assertEquals("|     x              |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));
  }

  @Test
  public void testDrawAtTheCanvasBorders() {
    drawingService.addShape(new Rectangle(1, 1, 20, 4));

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|xxxxxxxxxxxxxxxxxxxx|", valueOf(canvas.content[1]));
    assertEquals("|x                  x|", valueOf(canvas.content[2]));
    assertEquals("|x                  x|", valueOf(canvas.content[3]));
    assertEquals("|xxxxxxxxxxxxxxxxxxxx|", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));
  }

  @Test
  public void testDrawOutOfTheCanvasAddShape() {
    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(0, 1, 20, 1)));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, 21, 1)));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 0, 1, 4)));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, 1, 5)));
  }

  @Test
  public void testDrawOutOfTheCanvasBucketFill() {
    assertThrows(IllegalArgumentException.class, () -> drawingService.bucketFill(0, 1, 'o'));

    assertThrows(IllegalArgumentException.class, () -> drawingService.bucketFill(21, 1, 'o'));

    assertThrows(IllegalArgumentException.class, () -> drawingService.bucketFill(1, 0, 'o'));

    assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(1, 5, 'o'));
  }

  @Test
  public void testDrawOnACompletelyBucketFilledCanvas() {
    drawingService.bucketFill(10, 3, 'o');
    drawingService.addShape(new Rectangle(14, 1, 18, 3));

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|oooooooooooooxxxxxoo|", valueOf(canvas.content[1]));
    assertEquals("|oooooooooooooxoooxoo|", valueOf(canvas.content[2]));
    assertEquals("|oooooooooooooxxxxxoo|", valueOf(canvas.content[3]));
    assertEquals("|oooooooooooooooooooo|", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));
  }

  @Test
  public void testUndoChanges() {
    drawingService.addShape(new Line(1, 2, 6, 2));
    drawingService.addShape(new Line(6, 3, 6, 4));
    drawingService.addShape(new Rectangle(14, 1, 18, 3));
    drawingService.bucketFill(10, 3, 'o');

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|oooooooooooooxxxxxoo|", valueOf(canvas.content[1]));
    assertEquals("|xxxxxxooooooox   xoo|", valueOf(canvas.content[2]));
    assertEquals("|     xoooooooxxxxxoo|", valueOf(canvas.content[3]));
    assertEquals("|     xoooooooooooooo|", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));

    drawingService.undoChange();

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|             xxxxx  |", valueOf(canvas.content[1]));
    assertEquals("|xxxxxx       x   x  |", valueOf(canvas.content[2]));
    assertEquals("|     x       xxxxx  |", valueOf(canvas.content[3]));
    assertEquals("|     x              |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));

    drawingService.undoChange();

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|                    |", valueOf(canvas.content[1]));
    assertEquals("|xxxxxx              |", valueOf(canvas.content[2]));
    assertEquals("|     x              |", valueOf(canvas.content[3]));
    assertEquals("|     x              |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));

    drawingService.undoChange();

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|                    |", valueOf(canvas.content[1]));
    assertEquals("|xxxxxx              |", valueOf(canvas.content[2]));
    assertEquals("|                    |", valueOf(canvas.content[3]));
    assertEquals("|                    |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));

    drawingService.undoChange();

    assertEquals("----------------------", valueOf(canvas.content[0]));
    assertEquals("|                    |", valueOf(canvas.content[1]));
    assertEquals("|                    |", valueOf(canvas.content[2]));
    assertEquals("|                    |", valueOf(canvas.content[3]));
    assertEquals("|                    |", valueOf(canvas.content[4]));
    assertEquals("----------------------", valueOf(canvas.content[5]));
  }
}

package com.cs.hometask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxx              |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));

    drawingService.addShape(new Line(6, 3, 6, 4));

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxx              |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     x              |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     x              |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));
  }

  @Test
  public void testAddRectangle() {
    drawingService.addShape(new Line(1, 2, 6, 2));
    drawingService.addShape(new Line(6, 3, 6, 4));
    drawingService.addShape(new Rectangle(14, 1, 18, 3));

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|             xxxxx  |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxx       x   x  |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     x       xxxxx  |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     x              |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));
  }

  @Test
  public void testAddBucketFill() {
    drawingService.addShape(new Line(1, 2, 6, 2));
    drawingService.addShape(new Line(6, 3, 6, 4));
    drawingService.addShape(new Rectangle(14, 1, 18, 3));
    drawingService.bucketFill(10, 3, 'o');

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|oooooooooooooxxxxxoo|", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxxooooooox   xoo|", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     xoooooooxxxxxoo|", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     xoooooooooooooo|", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));

    drawingService.bucketFill(15, 2, 'o');

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|oooooooooooooxxxxxoo|", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxxoooooooxoooxoo|", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     xoooooooxxxxxoo|", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     xoooooooooooooo|", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));
  }

  @Test
  public void testAddRectangleReversedXAndYInput() {
    drawingService.addShape(new Line(6, 2, 1, 2));
    drawingService.addShape(new Line(6, 4, 6, 3));
    drawingService.addShape(new Rectangle(18, 3, 14, 1));

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|             xxxxx  |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxx       x   x  |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     x       xxxxx  |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     x              |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));
  }

  @Test
  public void testDrawAtTheCanvasBorders() {
    drawingService.addShape(new Rectangle(1, 1, 20, 4));

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|xxxxxxxxxxxxxxxxxxxx|", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|x                  x|", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|x                  x|", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|xxxxxxxxxxxxxxxxxxxx|", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));
  }

  @Test
  public void testDrawOutOfTheCanvasAddShape() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(0, 1, 20, 1)));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, 21, 1)));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 0, 1, 4)));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.addShape(new Line(1, 1, 1, 5)));
  }

  @Test
  public void testDrawOutOfTheCanvasBucketFill() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(0, 1, 'o'));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(21, 1, 'o'));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(1, 0, 'o'));

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> drawingService.bucketFill(1, 5, 'o'));
  }

  @Test
  public void testDrawOnACompletelyBucketFilledCanvas() {
    drawingService.bucketFill(10, 3, 'o');
    drawingService.addShape(new Rectangle(14, 1, 18, 3));

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|oooooooooooooxxxxxoo|", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|oooooooooooooxoooxoo|", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|oooooooooooooxxxxxoo|", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|oooooooooooooooooooo|", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));
  }

  @Test
  public void testUndoChanges() {
    drawingService.addShape(new Line(1, 2, 6, 2));
    drawingService.addShape(new Line(6, 3, 6, 4));
    drawingService.addShape(new Rectangle(14, 1, 18, 3));
    drawingService.bucketFill(10, 3, 'o');

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|oooooooooooooxxxxxoo|", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxxooooooox   xoo|", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     xoooooooxxxxxoo|", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     xoooooooooooooo|", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));

    drawingService.undoChange();

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|             xxxxx  |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxx       x   x  |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     x       xxxxx  |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     x              |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));

    drawingService.undoChange();

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxx              |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|     x              |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|     x              |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));

    drawingService.undoChange();

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|xxxxxx              |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));

    drawingService.undoChange();

    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[0]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[1]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[2]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[3]));
    Assertions.assertEquals("|                    |", String.valueOf(canvas.content[4]));
    Assertions.assertEquals("----------------------", String.valueOf(canvas.content[5]));
  }
}

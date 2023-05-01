package com.cs.hometask;

public interface DrawingService {

  void addShape(Shape shape);

  void bucketFill(int x, int y, char c);

  void draw();

  boolean isCanvasCreated();

  void setCanvas(Canvas canvas);

  void clearCanvas();

  void undoChange();
}

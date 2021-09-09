package com.cs.hometask;

public interface DrawingService {

  void addShape(Shape shape);

  void bucketFill(int x, int y, char c);

  void draw();

  void clearCanvas();

  void undoChange();
}

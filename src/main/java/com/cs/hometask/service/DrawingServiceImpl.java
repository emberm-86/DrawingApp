package com.cs.hometask.service;

import com.cs.hometask.domain.Canvas;
import com.cs.hometask.domain.Shape;

public class DrawingServiceImpl implements DrawingService {

  private Canvas canvas;

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
    canvas.addShape(shape);
  }

  @Override
  public void bucketFill(int x, int y, char c) {
    if (!isCanvasCreated()) {
      System.out.println("The canvas has not been created!");
      return;
    }
    canvas.bucketFill(x, y, c);
  }

  @Override
  public void clearCanvas() {
    canvas.reset();
  }

  @Override
  public void undoChange() {
    canvas.undoChange();
  }

  @Override
  public void draw() {
    if (isCanvasCreated()) {
      canvas.draw();
    }
  }

  @Override
  public boolean isCanvasCreated() {
    return canvas != null;
  }
}

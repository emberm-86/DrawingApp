package com.cs.hometask.domain;

import java.util.Objects;

public class Coordinate {

  private final int y;

  private final int x;

  /*
  Canvas content array is indexed in reversed way if we compare with the input coordinates.
  One element in the array represents a row.
  It is easier to validate if we print the canvas content by row,
  which is a big help in the unit testing.
  */

  public Coordinate(int y, int x) {
    this.y = y;
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordinate coordinate = (Coordinate) o;
    return y == coordinate.y && x == coordinate.x;
  }

  @Override
  public int hashCode() {
    return Objects.hash(y, x);
  }
}

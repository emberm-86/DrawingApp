package com.cs.hometask;

import java.util.Objects;

public class Coordinate {

  public int y;
  public int x;

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

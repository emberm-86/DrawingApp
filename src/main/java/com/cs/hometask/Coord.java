package com.cs.hometask;

import java.util.Objects;

public class Coord {

  public int y;
  public int x;

  /*
   Canvas content array is indexed in reversed way if we compare with the input coordinates.
   One element in the array represents a row.
   It is more easy to validate if we print the canvas content by row,
   which is a big help in the unit testing.
   */
  public Coord(int y, int x) {
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
    Coord coord = (Coord) o;
    return y == coord.y && x == coord.x;
  }

  @Override
  public int hashCode() {
    return Objects.hash(y, x);
  }
}

package com.cs.hometask.domain;

public record Coordinate(int y, int x) {
  /*
  Canvas content array is indexed in reversed way if we compare with the input coordinates.
  One element in the array represents a row.
  It is easier to validate if we print the canvas content by row,
  which is a big help in the unit testing.
  */
}

package com.cs.hometask.util;

public class MenuPrinter {
  public static void printMenu() {
    System.out.println("========================");
    System.out.println("1. Create a new canvas (C w h)");
    System.out.println("2. Start drawing on the canvas by issuing various commands:");
    System.out.println(
        "(Line: L x1 y1 x2 y2; Rectangle: R x1 y1 x2 y2; BucketFill: B x y c; Undo changes: U)");
    System.out.println("3. Quit (Q)");
    System.out.println("========================");
    System.out.println("Type your choice in the corresponding format: ");
  }

  public static void printErrorMessage(String errorMessage) {
    System.out.println("Error: " + errorMessage);
  }
}

package com.cs.hometask;

import static com.cs.hometask.ShapeFactory.createShape;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MenuUtil {

  private static final char TOP_DOWN_BOUNDARY_CHAR = '-';
  private static final char LEFT_RIGHT_BOUNDARY_CHAR = '|';
  private static final List<String> VALID_TYPE_CODES = Arrays.asList("C", "R", "L", "B", "U");
  private static final List<Integer> VALID_ARG_NUMBERS = Arrays.asList(3, 5, 5, 4, 1);

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

  public static void printErrorMessage(DrawingService drawingService, String errorMessage) {
    System.out.println("Error: " + errorMessage);
    repaint(drawingService);
  }

  public static void repaint(DrawingService drawingService) {
    drawingService.draw();
    printMenu();
  }

  public static void launchSelectedMenu(String[] arguments, String typeCode,
      DrawingService drawingService) {

    switch (typeCode) {

      case "C":
        Canvas canvas = new Canvas(TOP_DOWN_BOUNDARY_CHAR, LEFT_RIGHT_BOUNDARY_CHAR,
            Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
        drawingService.setCanvas(canvas);
        break;

      case "R":
      case "L":
        drawingService.addShape(createShape(typeCode, arguments));
        break;

      case "B":
        drawingService.bucketFill(Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]),
            arguments[3].charAt(0));
        break;

      case "U":
        drawingService.undoChange();
        break;

      default:
        break;
    }
  }

  public static Map<Predicate<String>, String> createValidatorMap(
      Map<String, Integer> argNumByTypeCode, String[] arguments,
      String typeCode, DrawingService drawingService) {

    Map<Predicate<String>, String> argumentValidatorMap = new LinkedHashMap<>();

    argumentValidatorMap.put(t -> !VALID_TYPE_CODES.contains(t),
        "You have given invalid type code: " + typeCode);

    argumentValidatorMap.put(t -> !"C".equals(t) && drawingService == null,
        "You have not created the canvas!");

    argumentValidatorMap.put(t -> arguments.length != argNumByTypeCode.get(t),
        "You have given input with incorrect length!");

    return argumentValidatorMap;
  }

  public static Map<String, Integer> createArgNumByTypeCode() {
    return IntStream.range(0, VALID_TYPE_CODES.size()).boxed()
        .collect(Collectors.toMap(VALID_TYPE_CODES::get, VALID_ARG_NUMBERS::get));
  }
}

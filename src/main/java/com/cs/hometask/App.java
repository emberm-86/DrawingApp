package com.cs.hometask;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.cs.hometask.ShapeFactory.createShape;
import static java.lang.Integer.parseInt;
import static java.util.Locale.ROOT;
import static java.util.stream.Collectors.toMap;

/**
 * Console drawing application.
 *
 * @author Matyas Ember
 */
public class App {

  private static final char TOP_DOWN_BOUNDARY_CHAR = '-';
  private static final char LEFT_RIGHT_BOUNDARY_CHAR = '|';
  private static final List<String> VALID_TYPE_CODES = Arrays.asList("C", "R", "L", "B", "U");
  private static final List<Integer> VALID_ARG_NUMS = Arrays.asList(3, 5, 5, 4, 1);

  private static Canvas canvas;
  private static DrawingService drawingService;

  public static void main(String[] args) {
    Map<String, Integer> argNumByTypeCode = IntStream.range(0, VALID_TYPE_CODES.size()).boxed()
        .collect(toMap(VALID_TYPE_CODES::get, VALID_ARG_NUMS::get));

    printMenu();

    Scanner in = new Scanner(System.in);

    while (in.hasNext()) {
      String[] arguments = in.nextLine().split(" ");
      String typeCode = arguments[0].toUpperCase(ROOT);

      if ("Q".equals(typeCode)) {
        break;
      }

      if ("".equals(typeCode)) {
        continue;
      }

      Map<Predicate<String>, String> validatorMap = createValidatorMap(argNumByTypeCode, arguments,
          typeCode);

      String errorMsg = validatorMap.entrySet().stream().filter(e -> e.getKey().test(typeCode))
          .map(Entry::getValue).findFirst().orElse(null);

      if (errorMsg != null) {
        printErrorMessage(errorMsg);
        continue;
      }

      try {
        launchSelectedMenu(arguments, typeCode);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
      if (canvas != null) {
        drawingService.draw();
      }
      printMenu();
    }
    in.close();
    System.out.println("Good bye! :)");
  }

  private static Map<Predicate<String>, String> createValidatorMap(
      Map<String, Integer> argNumByTypeCode, String[] arguments, String typeCode) {

    Map<Predicate<String>, String> argumentValidatorMap = new LinkedHashMap<>();

    argumentValidatorMap.put(t -> !VALID_TYPE_CODES.contains(t),
        "You have given invalid type code: " + typeCode);
    argumentValidatorMap.put(t -> !"C".equals(t) && canvas == null,
        "You have not created the canvas!");
    argumentValidatorMap.put(t -> arguments.length != argNumByTypeCode.get(t),
        "You have given input with incorrect length!");
    return argumentValidatorMap;
  }

  private static void launchSelectedMenu(String[] arguments, String typeCode) {
    switch (typeCode) {
      case "C":
        canvas = new Canvas(TOP_DOWN_BOUNDARY_CHAR, LEFT_RIGHT_BOUNDARY_CHAR,
            parseInt(arguments[1]), parseInt(arguments[2]));
        drawingService = new DrawingServiceImpl(canvas);
        break;
      case "R":
      case "L":
        drawingService.addShape(createShape(typeCode, arguments));
        break;
      case "B":
        drawingService.bucketFill(parseInt(arguments[1]), parseInt(arguments[2]),
            arguments[3].charAt(0));
        break;
      case "U":
        drawingService.undoChange();
        break;
      default:
        break;
    }
  }

  private static void printMenu() {
    System.out.println("========================");
    System.out.println("1. Create a new canvas (C w h)");
    System.out.println("2. Start drawing on the canvas by issuing various commands:");
    System.out.println(
        "(Line: L x1 y1 x2 y2; Rectangle: R x1 y1 x2 y2; BucketFill: B x y c; Undo changes: U)");
    System.out.println("3. Quit (Q)");
    System.out.println("========================");
    System.out.println("Type your choice in the corresponding format: ");
  }

  private static void printErrorMessage(String errorMessage) {
    System.out.println("Error: " + errorMessage);
    if (canvas != null) {
      drawingService.draw();
    }
    printMenu();
  }
}

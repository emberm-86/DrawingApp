package com.cs.hometask.service;

import com.cs.hometask.domain.Canvas;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static com.cs.hometask.util.InputCheckUtil.createValidatorMap;
import static com.cs.hometask.util.InputCheckUtil.getTypeCode;
import static com.cs.hometask.util.MenuPrinter.printErrorMessage;
import static com.cs.hometask.util.MenuPrinter.printMenu;
import static com.cs.hometask.service.ShapeFactory.createShape;

public class MenuController {

  private final DrawingService drawingService;

  public MenuController(DrawingService drawingService) {
    this.drawingService = drawingService;
  }

  private static final char TOP_DOWN_BOUNDARY_CHAR = '-';
  private static final char LEFT_RIGHT_BOUNDARY_CHAR = '|';

  public void repaint() {
    drawingService.draw();
    printMenu();
  }

  public boolean checkQuit(String typeCode) {
    return "Q".equals(typeCode);
  }

  public boolean checkEmptyOrError(String[] arguments) {
    String typeCode = getTypeCode(arguments);
    return checkEmpty(typeCode) || checkError(arguments, typeCode);
  }

  private boolean checkEmpty(String typeCode) {
    return "".equals(typeCode);
  }

  private boolean checkError(String[] arguments, String typeCode) {
    Map<Predicate<String>, String> validatorMap =
        createValidatorMap(arguments, typeCode, drawingService.isCanvasCreated());

    Optional<String> errorMsg =
        validatorMap.entrySet().stream()
            .filter(e -> e.getKey().test(typeCode))
            .map(Map.Entry::getValue)
            .findFirst();

    if (errorMsg.isPresent()) {
      printErrorMessage(errorMsg.get());
      repaint();
      return true;
    }
    return false;
  }

  public void launchSelectedMenu(String[] arguments) {
    String typeCode = getTypeCode(arguments);
    switch (typeCode) {
      case "C":
        Canvas canvas =
            new Canvas(
                TOP_DOWN_BOUNDARY_CHAR,
                LEFT_RIGHT_BOUNDARY_CHAR,
                Integer.parseInt(arguments[1]),
                Integer.parseInt(arguments[2]));
        drawingService.setCanvas(canvas);
        break;

      case "R":
      case "L":
        drawingService.addShape(createShape(typeCode, arguments));
        break;

      case "B":
        drawingService.bucketFill(
            Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]), arguments[3].charAt(0));
        break;

      case "U":
        drawingService.undoChange();
        break;

      default:
        break;
    }
  }
}

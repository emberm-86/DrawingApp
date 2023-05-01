package com.cs.hometask;

import static com.cs.hometask.MenuUtil.createArgNumByTypeCode;
import static com.cs.hometask.MenuUtil.createValidatorMap;
import static com.cs.hometask.MenuUtil.launchSelectedMenu;
import static com.cs.hometask.MenuUtil.printErrorMessage;
import static com.cs.hometask.MenuUtil.printMenu;
import static com.cs.hometask.MenuUtil.repaint;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Console drawing application.
 *
 * @author Matyas Ember
 */
public class Application {

  public static void main(String[] args) {
    DrawingService drawingService = new DrawingServiceImpl();
    Map<String, Integer> argNumByTypeCode = createArgNumByTypeCode();

    printMenu();

    Scanner in = new Scanner(System.in);

    while (in.hasNext()) {
      String[] arguments = in.nextLine().split("\\s+");
      String typeCode = arguments[0].toUpperCase(Locale.ROOT);

      if ("Q".equals(typeCode)) {
        break;
      }

      if ("".equals(typeCode)) {
        continue;
      }

      Map<Predicate<String>, String> validatorMap = createValidatorMap(argNumByTypeCode, arguments,
          typeCode, drawingService);

      Optional<String> errorMsg = validatorMap.entrySet().stream()
          .filter(e -> e.getKey().test(typeCode))
          .map(Entry::getValue).findFirst();

      if (errorMsg.isPresent()) {
        printErrorMessage(drawingService, errorMsg.get());
        continue;
      }

      try {
        launchSelectedMenu(arguments, typeCode, drawingService);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }

      repaint(drawingService);
    }
    in.close();
    System.out.println("Good bye! :)");
  }
}

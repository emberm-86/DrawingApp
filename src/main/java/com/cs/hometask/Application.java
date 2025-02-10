package com.cs.hometask;

import com.cs.hometask.service.DrawingServiceImpl;
import com.cs.hometask.service.MenuController;
import com.cs.hometask.util.InputCheckUtil;
import com.cs.hometask.util.MenuPrinter;
import java.util.Scanner;

/**
 * Console drawing application.
 *
 * @author Matyas Ember
 */
public class Application {

  public static void main(String[] args) {
    MenuController menuController = new MenuController(new DrawingServiceImpl());
    MenuPrinter.printMenu();

    Scanner in = new Scanner(System.in);

    while (in.hasNext()) {
      String[] arguments = in.nextLine().split("\\s+");
      if (menuController.checkQuit(InputCheckUtil.getTypeCode(arguments))) {
        break;
      }
      if (menuController.checkEmptyOrError(arguments)) {
        continue;
      }

      try {
        menuController.launchSelectedMenu(arguments);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
      menuController.repaint();
    }
    in.close();
    System.out.println("Good bye! :)");
  }
}

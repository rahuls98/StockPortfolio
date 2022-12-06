import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import controllers.PortfolioController;
import controllers.PortfolioControllerImpl;
import controllers.PortfolioGUIController;
import controllers.PortfolioGUIControllerImpl;
import models.PortfolioModel;
import models.PortfolioModelImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

/**
 * Entry point of the Stock Portfolio application.
 */
public class StockPortfolio {

  /**
   * Main method of the Stock Portfolio application.
   */
  public static void main(String[] args) {
    String userName = "Test";
    InputStream input = System.in;
    PrintStream out = System.out;
    PortfolioModel model = null;
    try {
      model = new PortfolioModelImpl(userName);
    } catch (Exception e) {
      System.out.println("Storage invalid/corrupted!");
      return;
    }

    while (true) {
      System.out.println("Which interface do you want to use?");
      System.out.println("1. Text based interface");
      System.out.println("2. Graphical user interface");
      System.out.println("3. Exit");
      System.out.print("Enter choice: ");
      Scanner sc = new Scanner(System.in);
      int ch = sc.nextInt();
      if (ch == 1) {
        PortfolioView view = new PortfolioViewImpl(out);
        PortfolioController controller = new PortfolioControllerImpl(model, view, input, out);
        controller.run();
      } else if (ch == 2) {
        PortfolioGUIController controller = new PortfolioGUIControllerImpl(model);
      } else if (ch == 3) {
        return;
      } else {
        System.out.println("\nInvalid choice, please try again!\n");
      }
    }
  }
}
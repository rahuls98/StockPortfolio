import java.io.InputStream;
import java.io.PrintStream;

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
    String userName = "test_user";
    InputStream input = System.in;
    PrintStream out = System.out;
    PortfolioModel model = null;
    try {
      model = new PortfolioModelImpl(userName);
    } catch (Exception e) {
      System.out.println("Storage invalid/corrupted!");
      return;
    }
    // if (args.length > 0 && args[0].equals("gui")) {
    if (true) {
      PortfolioGUIController controller = new PortfolioGUIControllerImpl(model);
    } else {
      PortfolioView view = new PortfolioViewImpl(out);
      PortfolioController controller = new PortfolioControllerImpl(model, view, input, out);
      controller.run();
    }
  }
}
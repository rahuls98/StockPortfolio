import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import controllers.PortfolioController;
import controllers.PortfolioControllerImpl;
import models.PortfolioModel;
import models.PortfolioModelImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

/**
 * Description of class.
 */
public class StockPortfolio {
  public static void main(String[] args) throws IOException {
    String userName = "Main";
    InputStream input = System.in;
    PrintStream out = System.out;
    PortfolioModel model = new PortfolioModelImpl(userName);
    PortfolioView view = new PortfolioViewImpl(out);
    PortfolioController controller = new PortfolioControllerImpl(model, view, userName, input, out);
    controller.go();
  }
}
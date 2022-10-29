import java.io.InputStream;

import controllers.PortfolioController;
import controllers.PortfolioControllerImpl;
import entities.User;
import models.PortfolioModel;
import models.PortfolioModelImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

/**
 * Description of class.
 */
public class StockPortfolio {
  public static void main(String[] args) {
    String userName = "default";
    User defaultUser = new User(userName);
    PortfolioModel model = new PortfolioModelImpl(userName);
    PortfolioView view = new PortfolioViewImpl();
    InputStream input = System.in;
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input);
    controller.go();
  }
}

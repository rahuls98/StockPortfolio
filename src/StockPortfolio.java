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
  public static void main(String[] args) {
    PortfolioModel model = new PortfolioModelImpl();
    PortfolioView view = new PortfolioViewImpl();
    PortfolioController controller = new PortfolioControllerImpl(model, view);
    controller.go();
  }
}

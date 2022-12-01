package views;

import controllers.PortfolioGUIController;

/**
 * The interface for our view class.
 */
public interface IView {
  void addFeatures(PortfolioGUIController features);

  void disappear();

  void displayDialog(Boolean flag, String message);

  void displayTable(String[] colName, Object[][] data);
}

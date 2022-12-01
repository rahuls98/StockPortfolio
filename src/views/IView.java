package views;

import controllers.PortfolioGUIController;

/**
 * The interface for our view class.
 */
public interface IView {
  /**
   * This method is used to link the GUI with methods in the controller.
   *
   * @param features An interface which defines the functionality of the GUI.
   */
  void addFeatures(PortfolioGUIController features);

  /**
   * Used to make the view disappear and no longer appear on the screen.
   */
  void disappear();

  /**
   * Used to popup a dialogue message on the screen.
   * Generally some input validation error message.
   *
   * @param flag    True for success, False for error.
   * @param message to be displayed to the user.
   */
  void displayDialog(Boolean flag, String message);

  /**
   * Used to display a table for the User.
   * Generally, some information the user has requested for.
   *
   * @param colName The heading of the columns.
   * @param data    the data to be displayed.
   */
  void displayTable(String[] colName, Object[][] data);
}

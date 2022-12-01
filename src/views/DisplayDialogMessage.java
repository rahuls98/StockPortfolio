package views;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controllers.PortfolioGUIController;

/**
 * GUI screen that displays a message to users on a dialog box.
 */
public class DisplayDialogMessage extends JFrame implements IView {
  private JOptionPane dialog;

  /**
   * Object of a GUI screen class that displays a message to users on a dialog box.
   */
  public DisplayDialogMessage(Boolean flag, String message) {
    if (flag) {
      JOptionPane.showMessageDialog(this, message);
    } else {
      JOptionPane.showMessageDialog(this, message, "Error",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    return;
  }

  @Override
  public void disappear() {
    return;
  }

  @Override
  public void displayDialog(Boolean flag, String message) {
    return;
  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {
    return;
  }
}

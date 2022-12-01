package views;

import javax.swing.*;

import controllers.PortfolioGUIController;

public class DisplayDialogMessage extends JFrame implements IView {
  private JOptionPane dialog;

  public DisplayDialogMessage(Boolean flag, String message) {
    if (flag) {
      JOptionPane.showMessageDialog(this, message);
    } else {
      JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void addFeatures(PortfolioGUIController features) {

  }

  @Override
  public void disappear() {

  }

  @Override
  public void displayDialog(Boolean flag, String message) {

  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {

  }
}

package View;
import javax.swing.*;

import Controller.*;

/**
 * The interface for our view class
 */
public interface IView
{
  void addFeatures(Features features);
  void disappear();
  void displayDialog(Boolean flag, String message);
  void displayTable(String[] colName, Object[][] data);
}

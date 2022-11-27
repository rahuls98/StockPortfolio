package View;

import java.io.File;

import javax.swing.*;
import Controller.*;

public class LoadPortfolioFromFile extends JFrame implements IView {
  JFileChooser chooser;
  JButton getFile;

  public LoadPortfolioFromFile() {
    super();


    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    getFile = new JButton("Attach File");
    getFile.setActionCommand("Attach File");

    chooser = new JFileChooser();


    this.add(getFile);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    getFile.addActionListener(e -> features.getFile(getFileFromUser()));
  }

  private File getFileFromUser() {
    chooser.showOpenDialog(this.getContentPane());
    return chooser.getSelectedFile();
  }

  @Override
  public void disappear() {
    setVisible(false);
  }

  @Override
  public void displayDialog(Boolean flag, String message) {
    if(flag) {
      JOptionPane.showMessageDialog(this, message);
    } else {
      JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {

  }


}

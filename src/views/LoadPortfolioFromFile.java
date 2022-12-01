package views;

import java.io.File;

import javax.swing.*;

import controllers.PortfolioGUIController;

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
  public void addFeatures(PortfolioGUIController features) {
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
    JFrame f = new JFrame();
    f.setSize(1000, 500);
    f.setLocation(200, 200);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTable table = new JTable(data, colName);

    table.setBounds(30, 40, 200, 300);
    JScrollPane sp = new JScrollPane(table);
    f.add(sp);
    f.setVisible(true);
  }
}

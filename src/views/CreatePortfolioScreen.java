package views;

import java.io.File;

import javax.swing.*;
import controllers.PortfolioGUIController;

public class CreatePortfolioScreen extends JFrame implements IView {
  JButton enterManually;
  JButton loadFile;
  JButton ret;

  public CreatePortfolioScreen() {
    super();

    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    enterManually = new JButton("Enter Manually");
    enterManually.setActionCommand("Enter Manually");

    loadFile = new JButton("Load File");
    loadFile.setActionCommand("Load File ");

    ret = new JButton("Return");
    ret.setActionCommand("Return");

    this.add(enterManually);
    this.add(loadFile);
    this.add(ret);

    pack();
    setVisible(true);

  }
  @Override
  public void addFeatures(PortfolioGUIController features) {
    enterManually.addActionListener(e -> features.goToCreatePortfolioManually());
    loadFile.addActionListener(e -> features.getFile(getFileFromUser()));
    ret.addActionListener(e -> features.goToHome());
  }

  private File getFileFromUser() {
    JFileChooser chooser = new JFileChooser(new File("./"));
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

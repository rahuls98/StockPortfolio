package View;

import java.io.File;

import javax.swing.*;
import Controller.Features;

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
  public void addFeatures(Features features) {
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

  }

}

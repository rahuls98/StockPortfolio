package View;

import java.awt.*;

import javax.swing.*;

import Controller.Features;

public class DisplayTable extends JFrame implements IView {
  private JTable table;
  private JScrollPane sp;
  private JButton exit;

  public DisplayTable(String[] colName, Object[][] data) {
    super();
    setSize(1000, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    table = new JTable(data, colName);

    table.setBounds(30, 40, 200, 300);
    sp = new JScrollPane(table);
    this.add(sp);
    exit = new JButton("Exit");
    exit.setActionCommand("Exit");
    exit.addActionListener(e -> this.disappear());
    this.add(exit);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    exit.addActionListener(e -> this.disappear());
  }

  @Override
  public void disappear() {
    setVisible(false);
  }

  @Override
  public void displayDialog(Boolean flag, String message)  {

  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {

  }
}

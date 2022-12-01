package views;

import javax.swing.*;

import controllers.PortfolioGUIController;

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
  public void addFeatures(PortfolioGUIController features) {
    exit.addActionListener(e -> this.disappear());
  }

  @Override
  public void disappear() {
    setVisible(false);
  }

  @Override
  public void displayDialog(Boolean flag, String message)  {
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

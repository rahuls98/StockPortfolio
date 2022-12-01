package views;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controllers.PortfolioGUIController;

/**
 * GUI screen that displays a table to users.
 */
public class DisplayTable extends JFrame implements IView {
  private final JButton exit;

  /**
   * Object of a GUI screen that displays a table to users.
   */
  public DisplayTable(String[] colName, Object[][] data) {
    super();
    setSize(1000, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    JTable table = new JTable(data, colName);

    table.setBounds(30, 40, 200, 300);
    JScrollPane sp = new JScrollPane(table);
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
  public void displayDialog(Boolean flag, String message) {
    return;
  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {
    return;
  }
}

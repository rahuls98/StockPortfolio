package views;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import controllers.PortfolioGUIController;

/**
 * GUI screen that allows users to create portfolios manually.
 */
public class CreateManualPortfolio extends JFrame implements IView {
  private final JTextField portfolioName;
  private final JButton createPortfolioButton;

  /**
   * Object of a GUI screen class that allows users to create portfolios manually.
   */
  public CreateManualPortfolio() {
    super();
    setSize(1000, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    JLabel enterName = new JLabel("Enter name of Portfolio(No spaces allowed)");
    portfolioName = new JTextField();
    createPortfolioButton = new JButton("Create");
    createPortfolioButton.setActionCommand("Create Portfolio");

    this.add(enterName);
    this.add(portfolioName);
    this.add(createPortfolioButton);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    createPortfolioButton.addActionListener(e -> features.createNewPortfolio(
            portfolioName.getText()));
  }

  @Override
  public void disappear() {
    this.setVisible(false);
  }

  @Override
  public void displayDialog(Boolean flag, String message) {
    if (flag) {
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

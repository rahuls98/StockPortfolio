package views;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

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
    return;
  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {
    return;
  }
}

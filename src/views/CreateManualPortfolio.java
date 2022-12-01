package views;

import javax.swing.*;

import controllers.PortfolioGUIController;

public class CreateManualPortfolio extends JFrame implements IView {
  private JLabel enterName;
  private JTextField portfolioName;
  private JButton createPortfolioButton;

  public CreateManualPortfolio() {
    super();
    setSize(1000, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    enterName = new JLabel("Enter name of Portfolio(No spaces allowed)");
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
    createPortfolioButton.addActionListener(e -> features.createNewPortfolio(portfolioName.getText()));
  }

  @Override
  public void disappear() {
    this.setVisible(false);
  }

  @Override
  public void displayDialog(Boolean flag, String message) {

  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {

  }
}

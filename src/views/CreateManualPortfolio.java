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

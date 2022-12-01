package views;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controllers.PortfolioGUIController;

/**
 * GUI screen that displays the application home screen to users.
 */
public class HomeScreen extends JFrame implements IView {
  private final JButton createPortfolio;
  private final JButton createOrder;
  private final JButton getComp;
  private final JButton getValue;
  private final JButton getCostBasis;
  private final JButton exit;
  private final JButton investmentPlan;
  private final JButton portfolioPerformance;

  /**
   * Object of a GUI screen class that displays the application home screen to users.
   */
  public HomeScreen(String caption) {
    super(caption);

    setSize(1000, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    createPortfolio = new JButton("Create Portfolio");
    createPortfolio.setActionCommand("Create Portfolio");
    createOrder = new JButton("Create Order");
    createOrder.setActionCommand("Create Order");
    getComp = new JButton("Get Portfolio Composition");
    getComp.setActionCommand("Get Portfolio Composition");
    getValue = new JButton("Get Value");
    getValue.setActionCommand("Get Value");
    getCostBasis = new JButton("Cost Basis");
    getCostBasis.setActionCommand("Cost Basis");
    investmentPlan = new JButton("Investment Plans");
    investmentPlan.setActionCommand("Investment Plans");
    portfolioPerformance = new JButton("Portfolio Performance");
    portfolioPerformance.setActionCommand("Portfolio Performance");
    exit = new JButton("Exit");
    exit.setActionCommand("Exit");

    this.add(createOrder);
    this.add(createPortfolio);
    this.add(investmentPlan);
    this.add(getComp);
    this.add(getValue);
    this.add(portfolioPerformance);
    this.add(getCostBasis);
    this.add(exit);

    pack();
    setVisible(true);

  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    exit.addActionListener(e -> features.exitProgram());
    createPortfolio.addActionListener(e -> features.goToCreatePortfolio());
    getComp.addActionListener(e -> features.goToDisplayPortfoliosComp());
    getValue.addActionListener(e -> features.goToDisplayValue());
    createOrder.addActionListener(e -> features.goToCreateOrder());
    investmentPlan.addActionListener(e -> features.goToInvestmentPlanScreen());
    portfolioPerformance.addActionListener(e -> features.goToPortfolioPerformance());
    getCostBasis.addActionListener(e -> features.goToCostBasis());
  }

  @Override
  public void disappear() {
    setVisible(false);
  }

  @Override
  public void displayDialog(Boolean flag, String message) {
    if (flag) {
      JOptionPane.showMessageDialog(this, message);
    } else {
      JOptionPane.showMessageDialog(this, message, "Error",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {
    return;
  }


}

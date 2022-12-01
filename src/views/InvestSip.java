package views;

import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.PortfolioGUIController;

/**
 * GUI screen that allows users to make SIP investments.
 */
public class InvestSip extends JFrame implements IView {
  private final JTextField[] percentages;
  private final JTextField[] stockNames;
  private final JPanel stockPanel;
  private final int maxStocks = 20;
  private int stockCount;
  private final String portfolioName;
  private final JTextField commission;
  private final JTextField amount;
  private final JButton addStockButton;
  private final JTextField startDate;
  private final JTextField endDate;
  private final JTextField interval;
  private final JButton submit;
  private final JButton ret;

  /**
   * Object of a GUI screen class that allows users to make SIP investments.
   */
  public InvestSip(String portfolioName, String[] stocks) {
    super();
    this.portfolioName = portfolioName;
    setBounds(300, 90, 1000, 5000);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    JPanel panel;
    if (stocks.length > 0) {
      this.add(new JLabel("Enter percentages from your below stocks"));
    }
    percentages = new JTextField[this.maxStocks];
    stockNames = new JTextField[this.maxStocks];
    stockPanel = new JPanel();
    stockCount = 0;
    stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.Y_AXIS));
    for (int i = 0; i < stocks.length; i++) {
      stockCount++;
      panel = new JPanel();
      stockNames[i] = new JTextField(stocks[i]);
      panel.add(stockNames[i]);
      percentages[i] = new JTextField("0", 4);
      panel.add(percentages[i]);
      stockPanel.add(panel);
    }
    this.add(stockPanel);
    addStockButton = new JButton("Add Stock");
    addStockButton.setActionCommand("Add Stock");
    this.add(addStockButton);

    this.add(new JLabel("Enter Amount"));
    amount = new JTextField(10);
    this.add(amount);
    this.add(new JLabel("Enter Start Date in YYYY-MM-DD"));
    startDate = new JTextField(10);
    this.add(startDate);
    this.add(new JLabel("Enter End Date in YYYY-MM-DD"));
    endDate = new JTextField(LocalDate.now().minusDays(1).toString(), 10);
    this.add(endDate);
    this.add(new JLabel("Enter Commission"));
    commission = new JTextField(10);
    this.add(commission);
    this.add(new JLabel("Enter Interval in Days"));
    interval = new JTextField(10);
    this.add(interval);

    submit = new JButton("Submit");
    submit.setActionCommand("Submit");
    ret = new JButton("Return");
    ret.setActionCommand("Return");

    this.add(submit);
    this.add(ret);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    addStockButton.addActionListener(e -> this.addStock());
    ret.addActionListener(e -> features.goToHome());
    submit.addActionListener(e -> features.investBySIP(
            this.portfolioName,
            amount.getText(),
            startDate.getText(),
            endDate.getText(),
            interval.getText(),
            this.returnStocks(),
            commission.getText()
    ));
  }

  private HashMap<String, String> returnStocks() {
    HashMap<String, String> stocks = new HashMap<>();
    for (int i = 0; i < stockCount; i++) {
      stocks.put(stockNames[i].getText(), percentages[i].getText());
    }
    return stocks;
  }

  private void addStock() {
    if (stockCount >= maxStocks) {
      return;
    }
    JPanel panel = new JPanel();
    stockNames[stockCount] = new JTextField(4);
    panel.add(stockNames[stockCount]);
    percentages[stockCount] = new JTextField("0", 4);
    panel.add(percentages[stockCount]);
    stockCount += 1;
    stockPanel.add(panel);

    stockPanel.validate();
    stockPanel.repaint();
    pack();
    this.getContentPane().validate();
    this.getContentPane().repaint();
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

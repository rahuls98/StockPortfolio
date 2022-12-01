package views;

import java.util.HashMap;

import javax.swing.*;

import controllers.PortfolioGUIController;

public class InvestByPercentage extends JFrame implements IView {
  private JTextField[] percentages;
  private JTextField[] stockNames;
  private JPanel stockPanel;
  private int maxStocks = 20;
  private int stockCount;
  private String portfolioName;
  private JTextField commission;
  private JTextField amount;
  private JButton addStockButton;
  private JTextField date;
  private JButton submit;
  private JButton ret;

  public InvestByPercentage(String portfolioName, String[] stocks) {
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
    this.add(new JLabel("Enter date in YYYY-MM-DD"));
    date = new JTextField(10);
    this.add(date);
    this.add(new JLabel("Enter Commission"));
    commission = new JTextField(10);
    this.add(commission);

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
    submit.addActionListener(e -> features.investByPercentage(
            this.portfolioName,
            this.amount.getText(),
            this.date.getText(),
            this.returnStocks(),
            this.commission.getText()
    ));
  }

  private HashMap<String, String> returnStocks() {
    HashMap<String, String> stocks = new HashMap<>();
    for(int i = 0; i < stockCount; i++){
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

  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {

  }
}
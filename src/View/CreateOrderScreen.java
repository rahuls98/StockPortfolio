package View;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.JComponent;

import Controller.Features;

public class CreateOrderScreen extends JFrame implements IView {
  private final int maxSize = 10;
  private JComboBox portfolioCombobox;
  private int count = 1;
  private JPanel panel1;
  private JPanel panel2;
  private JTextField[] tickers;
  private JTextField[] quantity;
  private JButton addStockButton;
  private JLabel enterCommission;
  private JTextField commission;
  private JLabel enterDate;
  private JTextField date;
  private JButton buyButton;
  private JButton sellButton;
  private JButton ret;

  public CreateOrderScreen(String message, String[] portfolios) {
    super(message);

    setBounds(300, 90, 1000, 5000);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    portfolioCombobox = new JComboBox(portfolios);
    this.add(new JLabel("Select Portfolio From Below"));
    this.add(portfolioCombobox);
    this.add(new JLabel("Enter Stocks"));
    panel1 = new JPanel();
    panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

    tickers = new JTextField[10];
    tickers[count] = new JTextField(10);

    quantity = new JTextField[10];
    quantity[count] = new JTextField(10);
    quantity[count].setToolTipText("Quantity" + count);

    panel1.add(new JLabel("Enter Ticker for Stock " + count));
    panel1.add(tickers[count]);
    panel1.add(new JLabel("Enter Quantity for Stock " + count));
    panel1.add(quantity[count]);


    this.getContentPane().add(panel1);

    addStockButton = new JButton("Add Stock");
    addStockButton.setActionCommand("Add Stock");
    this.add(addStockButton);

    enterCommission = new JLabel("Enter Commission");
    commission = new JTextField(10);
    this.add(enterCommission);
    this.add(commission);

    enterDate = new JLabel("Enter Date in YYYY-MM-DD");
    date = new JTextField(10);
    this.add(enterDate);
    this.add(date);

    panel2 = new JPanel();
    buyButton = new JButton("Buy");
    sellButton = new JButton("Sell");
    buyButton.setActionCommand("BUY");
    sellButton.setActionCommand("SELL");
    ret = new JButton("Return");
    ret.setActionCommand("Return");
    panel2.add(buyButton);
    panel2.add(sellButton);
    panel2.add(ret);

    this.add(panel2);

    JScrollPane jScrollPane = new JScrollPane();
    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.getContentPane().add(jScrollPane);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    addStockButton.addActionListener(e -> this.addStock());
    buyButton.addActionListener(e -> features.createOrder(
            ""+portfolioCombobox.getItemAt(portfolioCombobox.getSelectedIndex()),
            date.getText(),
            "BUY",
            //TODO: Handle invalid float in commision
            Float.parseFloat(commission.getText()),
            this.returnStocks()
    ));
    sellButton.addActionListener(e -> features.createOrder(
            ""+portfolioCombobox.getItemAt(portfolioCombobox.getSelectedIndex()),
            date.getText(),
            "SELL",
            //TODO: Handle invalid float in commision
            Float.parseFloat(commission.getText()),
            this.returnStocks()
    ));
    ret.addActionListener(e -> features.goToHome());
  }

  private void addStock() {
    if (count == maxSize) {
      //TODO:Handle message
      return;
    }
    count++;

    tickers[count] = new JTextField(10);
    quantity[count] = new JTextField(10);

    panel1.add(new JLabel("Enter Ticker for Stock " + count));
    panel1.add(tickers[count]);
    panel1.add(new JLabel("Enter Quantity for Stock " + count));
    panel1.add(quantity[count]);

    panel1.revalidate();
    panel1.repaint();
  }

  @Override
  public void disappear() {
    setVisible(false);
  }

  private HashMap<String, String> returnStocks() {
    HashMap<String, String> stocks = new HashMap<>();
    for(int i = 1; i <= count; i++){
      stocks.put(tickers[i].getText(), quantity[i].getText());
    }
    return stocks;
  }

  @Override
  public void displayDialog(Boolean flag, String message) {

  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {

  }
}

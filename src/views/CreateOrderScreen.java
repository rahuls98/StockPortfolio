package views;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controllers.PortfolioGUIController;

/**
 * GUI screen that allows users to create orders.
 */
public class CreateOrderScreen extends JFrame implements IView {
  private final JComboBox portfolioCombobox;
  private int count = 1;
  private final JPanel panel1;
  private final JTextField[] tickers;
  private final JTextField[] quantity;
  private final JButton addStockButton;
  private final JTextField commission;
  private final JTextField date;
  private final JButton buyButton;
  private final JButton sellButton;
  private final JButton ret;

  /**
   * Object of a GUI screen class that allows users to create orders.
   */
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

    JLabel enterCommission = new JLabel("Enter Commission");
    commission = new JTextField(10);
    this.add(enterCommission);
    this.add(commission);

    JLabel enterDate = new JLabel("Enter Date in YYYY-MM-DD");
    date = new JTextField(10);
    this.add(enterDate);
    this.add(date);

    JPanel panel2 = new JPanel();
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
  public void addFeatures(PortfolioGUIController features) {
    addStockButton.addActionListener(e -> this.addStock());
    buyButton.addActionListener(e -> features.createOrder(
            "" + portfolioCombobox.getItemAt(portfolioCombobox.getSelectedIndex()),
            date.getText(),
            "BUY",
            commission.getText(),
            this.returnStocks()
    ));
    sellButton.addActionListener(e -> features.createOrder(
            "" + portfolioCombobox.getItemAt(portfolioCombobox.getSelectedIndex()),
            date.getText(),
            "SELL",
            commission.getText(),
            this.returnStocks()
    ));
    ret.addActionListener(e -> features.goToHome());
  }

  private void addStock() {
    int maxSize = 10;
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
    for (int i = 1; i <= count; i++) {
      stocks.put(tickers[i].getText(), quantity[i].getText());
    }
    return stocks;
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

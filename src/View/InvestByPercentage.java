package View;

import javax.swing.*;

import Controller.Features;

public class InvestByPercentage extends JFrame implements IView {
  private JTextField[] percentages;
  private JTextField amount;
  private JTextField date;
  private JButton submit;
  private JButton ret;

  public InvestByPercentage(String[] stocks) {
    super();
    setBounds(300, 90, 1000, 5000);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    JPanel panel;
    if (stocks.length > 0) {
      this.add(new JLabel("Enter percentages from your below stocks"));
    }
    percentages = new JTextField[stocks.length];
    for (int i = 0; i < stocks.length; i++) {
      panel = new JPanel();
      panel.add(new JLabel(stocks[i]));
      percentages[i] = new JTextField("0",4);
      panel.add(percentages[i]);
      this.add(panel);
    }

    this.add(new JLabel("Enter Amount"));
    amount = new JTextField(10);
    this.add(amount);
    this.add(new JLabel("Enter date in YYYY-MM-DD"));
    date = new JTextField(10);
    this.add(date);

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
  public void addFeatures(Features features) {
    ret.addActionListener(e -> features.goToHome());
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

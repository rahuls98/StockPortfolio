package views;

import javax.swing.*;

import controllers.PortfolioGUIController;

public class PortfolioPerformanceScreen extends JFrame implements IView {
  private JPanel panel;
  private JTextField startDate;
  private JTextField endDate;
  private ButtonGroup group;
  private JButton display;
  private JButton ret;

  public PortfolioPerformanceScreen(String[] portfolios) {
    super();

    setSize(1000, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    group = new ButtonGroup();
    JRadioButton radioButton;

    for (int i = 0; i < portfolios.length; i++) {
      radioButton = new JRadioButton(portfolios[i]);
      radioButton.setActionCommand(portfolios[i]);
      //Group the radio buttons.
      group.add(radioButton);
      this.add(radioButton);
    }
    startDate = new JTextField(10);
    this.add(new JLabel("Enter start date in YYYY-MM-DD"));
    this.add(startDate);
    endDate = new JTextField(10);
    this.add(new JLabel("Enter end date in YYYY-MM-DD"));
    this.add(endDate);
    display = new JButton("Display Portfolio Performance");
    this.add(display);
    ret = new JButton("Return");
    this.add(ret);

    pack();
    setVisible(true);

  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    ret.addActionListener(e -> features.goToHome());
    display.addActionListener(e -> features.displayPortfolioPerformance(
            group.getSelection().getActionCommand(),
            startDate.getText(),
            endDate.getText()
    ));
  }

  @Override
  public void disappear() {

  }

  @Override
  public void displayDialog(Boolean flag, String message) {
    if(flag) {
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

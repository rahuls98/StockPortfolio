package views;

import javax.swing.*;

import controllers.PortfolioGUIController;

public class InvestmentPlanScreen extends JFrame implements IView {
  private JPanel panel;
  private ButtonGroup group;
  private JButton investByPercentage;
  private JButton investSip;
  private JButton ret;

  public InvestmentPlanScreen(String[] portfolios) {
    super();
    setBounds(300, 90, 1000, 5000);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

    this.add(new JLabel("Select portfolio to invest in:"));
    group = new ButtonGroup();
    JRadioButton radioButton;

    for (int i = 0; i < portfolios.length; i++) {
      radioButton = new JRadioButton(portfolios[i]);
      radioButton.setActionCommand(portfolios[i]);
      //Group the radio buttons.
      group.add(radioButton);
      this.add(radioButton);
    }

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    investByPercentage = new JButton("Invest By Percentage");
    investByPercentage.setActionCommand("Invest By Percentage");
    investSip = new JButton("Invest SIP");
    investSip.setActionCommand("InvestSIP");
    ret = new JButton("Return");
    ret.setActionCommand("Return");

    panel.add(investByPercentage);
    panel.add(investSip);
    panel.add(ret);

    this.add(panel);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    ret.addActionListener(e -> features.goToHome());
    investByPercentage.addActionListener(e -> features.goToInvestByPercentageScreen(group.getSelection().getActionCommand()));
    investSip.addActionListener(e -> features.goToInvestSipScreen(group.getSelection().getActionCommand()));
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

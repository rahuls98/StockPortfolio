package views;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import controllers.PortfolioGUIController;

/**
 * GUI screen that displays different investment strategies available to users.
 */
public class InvestmentPlanScreen extends JFrame implements IView {
  private final ButtonGroup group;
  private final JButton investByPercentage;
  private final JButton investSip;
  private final JButton ret;

  /**
   * Object of a GUI screen class that displays different investment strategies available to users.
   */
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

    JPanel panel = new JPanel();
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
    investByPercentage.addActionListener(e ->
            features.goToInvestByPercentageScreen(group.getSelection().getActionCommand()));
    investSip.addActionListener(e ->
            features.goToInvestSipScreen(group.getSelection().getActionCommand()));
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

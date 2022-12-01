package views;

import javax.swing.*;

import controllers.PortfolioGUIController;

public class GetCostBasisScreen extends JFrame implements IView {
  private JPanel panel;
  private JTextField date;
  private ButtonGroup group;
  private JButton display;
  private JButton ret;

  public GetCostBasisScreen(String[] portfolios){
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
    date = new JTextField(10);
    this.add(new JLabel("Enter date in YYYY-MM-DD"));
    this.add(date);
    display = new JButton("Display Cost Basis");
    this.add(display);
    ret = new JButton("Return");
    this.add(ret);

    pack();
    setVisible(true);

  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    ret.addActionListener(e -> features.goToHome());
    display.addActionListener(e -> features.getCostBasis(
            group.getSelection().getActionCommand(),
            date.getText()
    ));
  }

  @Override
  public void disappear() {
    setVisible(false);
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

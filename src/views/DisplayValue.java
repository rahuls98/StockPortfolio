package views;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import controllers.PortfolioGUIController;

/**
 * GUI screen that displays a value to users.
 */
public class DisplayValue extends JFrame implements IView {
  private final JTextField textField;
  private final ButtonGroup group;
  private final JButton display;
  private final JButton ret;

  /**
   * Object of a GUI screen that displays a value to users.
   */
  public DisplayValue(String[] portfolios) {
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
    JLabel dateLabel = new JLabel("Enter Date in YYYY-MM-DD Format");
    this.add(dateLabel);
    textField = new JTextField();
    this.add(textField);
    display = new JButton("Display Value");
    this.add(display);
    ret = new JButton("Return");
    this.add(ret);

    pack();
    setVisible(true);
  }


  @Override
  public void addFeatures(PortfolioGUIController features) {
    display.addActionListener(e -> features.displayPortfolioValue(
            group.getSelection().getActionCommand(), textField.getText()));
    ret.addActionListener(e -> features.goToHome());
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

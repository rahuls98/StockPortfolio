package views;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import controllers.PortfolioGUIController;

/**
 * GUI screen that displays a performance chart.
 */
public class PerformanceChart extends JFrame implements IView {
  private Graphics2D g2d;
  private TreeMap<String, Float> performance;
  private JButton exit;

  /**
   * Object of a GUI screen class that displays a performance chart.
   */
  public PerformanceChart(TreeMap<String, Float> performance) {
    super("Portfolio Performance");

    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.performance = performance;
    exit = new JButton("Return");

    setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    //XAxis
    g2d.drawLine(20, 250, 450, 250);
    //YAxis
    g2d.drawLine(20, 250, 20, 50);

    int x_length = 450 - 20;
    int y_length = 250 - 50;
    HashMap<Integer, Float> x_points = new HashMap<>();

    for (int i = 0; i < performance.size(); i++) {
      float prev = x_points.getOrDefault(i, (float) 20);
      x_points.put(i + 1, prev + (x_length / performance.size()));
    }

    for (int i = 0; i < x_points.size(); i++) {
      g2d.drawLine((int) Math.ceil(x_points.get(i + 1)), 248,
              (int) Math.ceil(x_points.get(i + 1)), 252);
    }
    int j = 1;
    for (Map.Entry<String, Float> entry : this.performance.entrySet()) {
      g2d.drawString(entry.getKey(), x_points.get(j) - 30, 275);
      j++;
    }


    float maxVal = 0;
    float minVal = Float.MAX_VALUE;
    for (Map.Entry<String, Float> val : performance.entrySet()) {
      maxVal = Math.max(maxVal, val.getValue());
      minVal = Math.min(minVal, val.getValue());
    }

    HashMap<Integer, Float> y_points = new HashMap<>();
    HashMap<Integer, Float> y_points_temp = new HashMap<>();
    int i = 1;
    for (Map.Entry<String, Float> val : performance.entrySet()) {
      y_points_temp.put(i, 250 - this.minMaxScaler(val.getValue(), minVal, maxVal));
      i++;
    }
    float minYVal = Float.MAX_VALUE;
    float maxYVal = 0;

    for (Map.Entry<Integer, Float> temp : y_points_temp.entrySet()) {
      minYVal = Math.min(minYVal, temp.getValue());
      maxYVal = Math.max(maxYVal, temp.getValue());
    }

    float val1;
    float minYVal1 = Float.MAX_VALUE;
    float maxYVal1 = 0;
    for (Map.Entry<Integer, Float> temp : y_points_temp.entrySet()) {
      val1 = this.minMaxScaler(temp.getValue(), minYVal, maxYVal);
      minYVal1 = Math.min(minYVal1, val1);
      maxYVal1 = Math.max(maxYVal1, val1);
      y_points.put(temp.getKey(), val1);
    }


    for (i = 0; i < y_points.size(); i++) {
      g2d.drawLine(18, (int) Math.ceil(y_points.get(i + 1)),
              22, (int) Math.ceil(y_points.get(i + 1)));
    }


    //Plotting points
    for (i = 0; i < performance.size(); i++) {
      g2d.drawOval((int) Math.ceil(x_points.get(i + 1)),
              (int) Math.ceil(y_points.get(i + 1)), 2, 2);
    }

    //Draw Lines
    for (i = 1; i <= performance.size() - 1; i++) {
      g2d.drawLine((int) Math.ceil(x_points.get(i)),
              (int) Math.ceil(y_points.get(i)),
              (int) Math.ceil(x_points.get(i + 1)),
              (int) Math.ceil(y_points.get(i + 1)));
    }

    Font currentFont = g2d.getFont();
    Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.8F);
    g.setFont(newFont);

    g2d.drawString(String.valueOf(Math.round(maxVal)), 7, 50);
    g2d.drawString(String.valueOf(Math.round(minVal)), 7, 250);

  }


  private float minMaxScaler(float x, float min, float max) {
    float scale = 250 - 50;
    float maxMin = max - min;
    float temp1 = scale * (x - min);
    float temp2 = temp1 / maxMin;
    return temp2 + 50;
  }

  @Override
  public void addFeatures(PortfolioGUIController features) {
    exit.addActionListener(e -> features.goToHome());
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

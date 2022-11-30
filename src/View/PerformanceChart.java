package View;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import Controller.Features;

public class PerformanceChart extends JFrame implements IView {
  private Graphics2D g2d;
  private TreeMap<String, Float> performance;

  public PerformanceChart(TreeMap<String, Float> performance) {
    super("Line Graph");

    setSize(500, 300);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
//    this.performance = performance;
    this.performance = new TreeMap<>();
    this.performance.put("2022-06-25", (float) 50);
    this.performance.put("2022-07-15", (float) 100);
    this.performance.put("2022-08-15", (float) 120);

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


    float maxVal = 0;
    float minVal = Float.MAX_VALUE;
    for (Map.Entry<String, Float> val : performance.entrySet()) {
      maxVal = Math.max(maxVal, val.getValue());
      minVal = Math.min(minVal, val.getValue());
    }

    //TODO: Handle max == min

    HashMap<Integer, Float> y_points = new HashMap<>();
    int i = 1;
    for (Map.Entry<String, Float> val : performance.entrySet()) {
      y_points.put(i, 250 - this.minMaxScaler(val.getValue(), minVal, maxVal));
      i++;
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


  }


  private float minMaxScaler(float x, float min, float max) {
    float scale = 250 - 50;
    float maxMin = max - min;
    return ((scale * (x - min)) / maxMin) + 50;
  }

  @Override
  public void addFeatures(Features features) {

  }

  @Override
  public void disappear() {

  }

  @Override
  public void displayDialog(Boolean flag, String message) {

  }

  @Override
  public void displayTable(String[] colName, Object[][] data) {

  }
}

package views;

import java.util.HashMap;
import java.util.Map;

/**
 * Description of class.
 */
public class PortfolioViewImpl implements PortfolioView {

  @Override
  public void displayActions() {
    String[] actions = new String[]{"Create portfolio", "Get portfolio composition",
            "Get portfolio value", "Exit"};
    for (int i = 0; i < actions.length; i++) {
      System.out.println((i + 1) + ". " + actions[i]);
    }
  }

  @Override
  public void displayPortfolioComposition(String name, HashMap<String, Integer> portfolio) {
    System.out.println("Portfolio: " + name + "\n");
    System.out.format("%-15s %-5s %-10s \n", "Stock", "|", "Quantity");
    System.out.format("%s", "-------------------------------\n");
    for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
      System.out.format("%-15s %-5s %-10s \n", entry.getKey(), "|", entry.getValue());
    }
  }
}

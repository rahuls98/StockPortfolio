package views;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a text-based user-interface view of the application.
 */
public class PortfolioViewImpl implements PortfolioView {

  @Override
  public void displayActions() {
    String[] actions = new String[]{"Create portfolio", "Get portfolio composition",
            "Get portfolio value", "Exit"};
    System.out.println("\n");
    for (int i = 0; i < actions.length; i++) {
      System.out.println((i + 1) + ". " + actions[i]);
    }
    System.out.print("Select action: ");
  }

  @Override
  public void displayPortfolios(String[] portfolios) {
    System.out.println("\n");
    for (int i = 0; i < portfolios.length; i++) {
      System.out.println((i + 1) + ". " + portfolios[i]);
    }
    System.out.print("Select portfolio: ");
  }

  @Override
  public void displayPortfolioComposition(String name, HashMap<String, Integer> portfolio) {
    System.out.println("\n");
    System.out.println("entities.Portfolio: " + name);
    System.out.format("%s", "-------------------------------\n");
    System.out.format("%-12s %-3s %-12s \n", "entities.Stock", "|", "Quantity");
    System.out.format("%s", "-------------------------------\n");
    for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
      System.out.format("%-12s %-3s %-12s \n", entry.getKey(), "|", entry.getValue());
    }
  }

  @Override
  public void displayPortfolioValue(String name, HashMap<String, Float> portfolioValues) {
    System.out.println("\n");
    System.out.println("entities.Portfolio: " + name);
    System.out.format("%s", "-------------------------------\n");
    System.out.format("%-12s %-3s %-12s \n", "entities.Stock", "|", "Value");
    System.out.format("%s", "-------------------------------\n");
    for (Map.Entry<String, Float> entry : portfolioValues.entrySet()) {
      System.out.format("%-12s %-3s %-12s \n", entry.getKey(), "|", entry.getValue());
    }
  }
}

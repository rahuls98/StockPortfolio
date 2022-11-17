package views;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a text-based user-interface view of the application.
 */
public class PortfolioViewImpl implements PortfolioView {

  private final PrintStream out;

  /**
   * Returns an object of the PortfolioViewImpl class.
   */
  public PortfolioViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void displayActions(String[] actions) {
    for (int i = 0; i < actions.length; i++) {
      this.out.println((i + 1) + ". " + actions[i]);
    }
  }

  @Override
  public void displayPortfolios(String[] portfolios) {
    if (portfolios == null) {
      throw new IllegalArgumentException("Input cannot be null!");
    }
    if (portfolios.length == 0) {
      this.out.println("You have no portfolios!");
      return;
    }
    for (int i = 0; i < portfolios.length; i++) {
      this.out.println((i + 1) + ". " + portfolios[i]);
    }
  }

  @Override
  public void displayPortfolioComposition(String name, HashMap<String, Integer> stockQuantities) {
    if (name == null || stockQuantities == null) {
      throw new IllegalArgumentException("Input cannot be null!");
    }
    if (name.equals("") || stockQuantities.isEmpty()) {
      this.out.println("Portfolio is currently Empty");
      return;
    }
    this.out.println("Portfolio: " + name);
    this.out.format("%s", "---------------------------\n");
    this.out.format("%-12s%-3s%-12s\n", "Stock", "|", "Quantity");
    this.out.format("%s", "---------------------------\n");
    for (Map.Entry<String, Integer> entry : stockQuantities.entrySet()) {
      this.out.format("%-12s%-3s%-12s\n", entry.getKey(), "|", entry.getValue());
    }
    this.out.format("%s", "---------------------------\n");
  }

  @Override
  public void displayPortfolioValue(String name, HashMap<String, Float> portfolioValues) {
    if (name == null || portfolioValues == null) {
      throw new IllegalArgumentException("Input cannot be null!");
    }
    if (name.equals("") || portfolioValues.isEmpty()) {
      return;
    }
    this.out.println("Portfolio: " + name);
    this.out.format("%s", "---------------------------\n");
    this.out.format("%-12s%-3s%-12s\n", "Stock", "|", "Value");
    this.out.format("%s", "---------------------------\n");
    for (Map.Entry<String, Float> entry : portfolioValues.entrySet()) {
      this.out.format("%-12s%-3s%-12s\n", entry.getKey(), "|", "$" + entry.getValue());
    }
    this.out.format("%s", "---------------------------\n");
  }

  @Override
  public void displayCostBasis(String name, String date, Float costBasis) {
    this.out.println("Cost basis of " + name + " as of " + date + " is $" + costBasis);
  }

  @Override
  public void displayPerformance(String portfolioName,
                                 TreeMap<String, Float> performanceValues, float scale) {
    this.out.println();
    this.out.println("Performance of portfolio " + portfolioName
            + " from " + performanceValues.firstKey() + " to " + performanceValues.lastKey());
    this.out.println("Scale: * = $" + String.format("%.2f", scale));
    this.out.println();
    int strCounter;
    for (Map.Entry<String, Float> mapEntry : performanceValues.entrySet()) {
      this.out.print(mapEntry.getKey() + ": ");
      strCounter = 0;
      for (int b = 0; b < (int) (mapEntry.getValue() / scale); b++) {
        if (strCounter == 50) {
          break;
        }
        strCounter += 1;
        this.out.print("*");
      }
      this.out.println();
    }
  }
}

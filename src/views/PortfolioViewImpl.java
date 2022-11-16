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
      throw new IllegalArgumentException("Input cannot be empty!");
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
      this.out.format("%-12s%-3s%-12s\n", entry.getKey(), "|", entry.getValue());
    }
    this.out.format("%s", "---------------------------\n");
  }

  @Override
  public void displayCostBasis(String name, String date, Float costBasis) {
    this.out.println("Cost Basis of " +name+"on date" +date+" is " +costBasis);
  }

  @Override
  public void displayPerformance(TreeMap<String, Float> performanceValues, float scale) {
    System.out.println("Scale: * = $" + (int) scale);
    for (Map.Entry<String, Float> mapEntry : performanceValues.entrySet()) {
      // LocalDate ld = LocalDate.parse(mapEntry.getKey());
      // System.out.print(ld.getMonth().toString().substring(0,3) + ", " + ld.getYear() + ": ");
      System.out.print(mapEntry.getKey() + ": ");
      for (int b = 0; b < (int) (mapEntry.getValue() / scale); b++) {
        System.out.print("*");
      }
      System.out.println();
    }
  }
}

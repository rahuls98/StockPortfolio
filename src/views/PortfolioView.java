package views;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Represents all operations associated to the user-view of the application.
 */
public interface PortfolioView {

  /**
   * Displays all actions that can be performed by the user.
   *
   * @param actions List of actions to display.
   */
  void displayActions(String[] actions);

  /**
   * Displays list of saved portfolios.
   *
   * @param portfolios List of portfolios.
   */
  void displayPortfolios(String[] portfolios);

  /**
   * Displays the composition of a single portfolio.
   *
   * @param name            Name of the portfolio.
   * @param stockQuantities Contents of the portfolio.
   */
  void displayPortfolioComposition(String name, HashMap<String, Float> stockQuantities);

  /**
   * Displays the value of a single portfolio.
   *
   * @param name            Name of the portfolio.
   * @param portfolioValues Contents of the portfolio with their values.
   */
  void displayPortfolioValue(String name, HashMap<String, Float> portfolioValues);

  /**
   * Display cost basis of the portfolio.
   * @param name of portfolio.
   * @param date of cost basis.
   * @param costBasis of portfolio.
   */
  void displayCostBasis(String name, String date, Float costBasis);

  /**
   * display the performance of the portfolio.
   * @param portfolioName of the portfolio.
   * @param performanceValues to be displayed.
   * @param scale for displaying the performance.
   */
  void displayPerformance(String portfolioName, TreeMap<String, Float> performanceValues,
                          float scale);
}

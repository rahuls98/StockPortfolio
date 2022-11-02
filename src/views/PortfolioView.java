package views;

import java.util.HashMap;

/**
 * Represents all operations associated to the user-view of the application.
 */
public interface PortfolioView {

  /**
   * Displays all actions that can be performed by the user.
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
  void displayPortfolioComposition(String name, HashMap<String, Integer> stockQuantities);

  /**
   * Displays the value of a single portfolio.
   *
   * @param name            Name of the portfolio.
   * @param portfolioValues Contents of the portfolio with their values.
   */
  void displayPortfolioValue(String name, HashMap<String, Float> portfolioValues);
}

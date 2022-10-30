package views;

import java.util.HashMap;

import entities.Portfolio;

/**
 * Represents all operations associated to the user-view of the application.
 */
public interface PortfolioView {

  /**
   * Displays all actions that can be performed by the user.
   *
   * @return
   */
  Object displayActions();

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
   * Description of method.
   *
   * @param name            desc.
   * @param portfolioValues desc.
   */
  void displayPortfolioValue(String name, HashMap<String, Float> portfolioValues);
}

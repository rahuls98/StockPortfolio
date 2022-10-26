package controllers;

import java.util.HashMap;

/**
 * Description of interface.
 */
public interface PortfolioController {

  /**
   * Description of method.
   */
  void go();

  HashMap<String, HashMap<String, Integer>> readPortfolioStocks();
}

package models;

import java.util.HashMap;

/**
 * Description of interface.
 */
public interface PortfolioModel {

  /**
   * Description of method.
   *
   * @param portfolio desc.
   */
  void updatePortfolio(HashMap<String, HashMap<String, Integer>> portfolio);

  /**
   * Description of method.
   *
   * @param name desc.
   * @return desc.
   */
  HashMap<String, Integer> getPortfolio(String name);

  /**
   * Description of method.
   *
   * @return desc.
   */
  String[] getPortfolios();

  /**
   * Returns the stock name, Current value from the portfolio.
   * @return Hashmap containing name, value.
   */
  HashMap<String, Float> getPortfolioValues(String name, String date);

  Float getPortfolioTotal(HashMap<String, Float> portfolioValues);

}

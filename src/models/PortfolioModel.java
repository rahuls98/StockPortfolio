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

}

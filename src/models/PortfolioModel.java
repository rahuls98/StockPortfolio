package models;

import java.util.HashMap;

/**
 * Description of interface.
 */
public interface PortfolioModel {

  void updatePortfolio(HashMap<String, HashMap<String, Integer>> portfolio);

  HashMap<String, Integer> getPortfolio(String name);

  String[] getPortfolios();

}

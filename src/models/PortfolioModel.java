package models;

import java.util.HashMap;

import entities.Portfolio;
import entities.User;

/**
 * Description of interface.
 */
public interface PortfolioModel {

  /**
   * Updated the local Storage with User.
   *
   * @param portfolio desc.
   */
  void updatePortfolio(User user);

  /**
   * Returns Stock Names & Quantity.
   *
   * @param name desc.
   * @return desc.
   */
  Portfolio getPortfolio(String name);

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

  Float getPortfolioTotal(String name, String date);

}

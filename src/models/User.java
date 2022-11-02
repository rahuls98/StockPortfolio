package models;

import java.util.HashMap;

/**
 * Description of class.
 */
public class User {

  private String name;
  private HashMap<String, Portfolio> portfolios;

  /**
   * Description of constructor.
   *
   * @param name desc.
   */
  public User(String name) throws IllegalArgumentException {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Username cannot be empty!");
    }
    this.name = name;
    this.portfolios = new HashMap<>();
  }

  /**
   * Description of method.
   *
   * @return desc.
   */
  public String getName() {
    return name;
  }

  /**
   * Description of method.
   *
   * @param portfolio desc.
   */
  public void addPortfolio(Portfolio portfolio) {
    if (portfolio == null) {
      throw new IllegalArgumentException("Null portfolio not allowed!");
    }
    portfolios.put(portfolio.getName(), portfolio);
  }

  /**
   * Description of method.
   *
   * @return desc.
   */
  public HashMap<String, Portfolio> getPortfolios() {
    return portfolios;
  }
}

package entities;

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
  public User(String name) {
    // TODO : validate usernames for invalid characters, null, empty strings
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
    // TODO : validate not null
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

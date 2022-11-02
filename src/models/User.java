package models;

import java.util.HashMap;

/**
 * Represents a User having a unique username and multiple portfolios.
 */
class User {

  private String name;
  private HashMap<String, Portfolio> portfolios;

  /**
   * Returns an object of User.
   *
   * @param name Unique username.
   */
  public User(String name) throws IllegalArgumentException {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Username cannot be empty!");
    }
    this.name = name;
    this.portfolios = new HashMap<>();
  }

  /**
   * Returns the username of the User.
   *
   * @return username.
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a portfolio to the User.
   *
   * @param portfolio Portfolio to add.
   */
  public void addPortfolio(Portfolio portfolio) {
    if (portfolio == null) {
      throw new IllegalArgumentException("Null portfolio not allowed!");
    }
    portfolios.put(portfolio.getName(), portfolio);
  }

  /**
   * Returns all the portfolios of the User.
   *
   * @return Portfolios of the User.
   */
  public HashMap<String, Portfolio> getPortfolios() {
    return portfolios;
  }
}

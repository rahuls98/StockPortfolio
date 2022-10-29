package entities;

import java.util.HashMap;

public class User {

  private String name;
  private HashMap<String, Portfolio> portfolios;

  public User(String name) {
    // TODO : validate user names for invalid characters, null, empty strings
    this.name = name;
    this.portfolios = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void addPortfolio(Portfolio portfolio) {
    // TODO : validate not null
    portfolios.put(portfolio.getName(), portfolio);
  }

  public HashMap<String, Portfolio> getPortfolios() {
    return portfolios;
  }
}

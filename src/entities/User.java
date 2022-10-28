package entities;

import java.util.HashMap;

public class User {

  private String name;
  private HashMap<String, Portfolio> portfolios;

  public User(String name) {
    this.name = name;
    this.portfolios = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void addPortfolio(Portfolio portfolio) {
    portfolios.put(this.name, portfolio);
  }

  public HashMap<String, Portfolio> getPortfolios() {
    return portfolios;
  }
}

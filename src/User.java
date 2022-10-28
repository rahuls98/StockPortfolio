import java.util.HashMap;

public class User {

  private String name;
  private HashMap<String, Portfolio> portfolios;

  public User(String name) {
    this.name = name;
    this.portfolios = new HashMap<>();
  }

  public void addPortfolio(Portfolio portfolio) {
    portfolios.put(this.name, portfolio);
  }

}

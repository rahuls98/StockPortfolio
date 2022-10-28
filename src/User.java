import java.util.ArrayList;
import java.util.HashMap;

public class User {

  private String userName;
  private HashMap<String, Portfolio> portfolios;

  public User(String name) {
    this.userName = name;
    this.portfolios = new HashMap<>();
  }

  public void addPortfolio(Portfolio portfolio) {
    portfolios.put(this.userName, portfolio);
  }
}

import java.util.ArrayList;

public class User {

  private ArrayList<Portfolio> portfolios;

  public User() {
    this.portfolios = new ArrayList<>();
  }

  public void addPortfolio(Portfolio portfolio) {
    portfolios.add(portfolio);
  }
}

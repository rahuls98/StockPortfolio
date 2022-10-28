import java.util.ArrayList;
import java.util.HashMap;

public class Portfolio {

  private String name;
  private HashMap<Stock, Integer> stocks;

  public Portfolio(String name) {
    this.name = name;
    this.stocks = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void addStock(Stock stock, int quantity) {
    stocks.put(stock, quantity);
  }
}

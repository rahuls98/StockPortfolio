import java.util.ArrayList;
import java.util.HashMap;

public class Portfolio {

  private String name;
  private ArrayList<Stock> stocks;
  private HashMap<String, Integer> quantities;

  public Portfolio(String name) {
    this.name = name;
    this.stocks = new ArrayList<>();
    this.quantities = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void addStock(Stock stock, int quantity) {
    stocks.add(stock);
    quantities.put(stock.getTicker(), quantity);
  }
}

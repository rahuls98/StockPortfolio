package entities;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {

  private String name;
  private HashMap<Stock, Integer> stocks;

  private String lastPossibleValue;

  public Portfolio(String name) {
    // TODO : handle null, empty, invalid strings
    this.name = name;
    this.stocks = new HashMap<>();
    this.lastPossibleValue = "0";
  }

  public HashMap<String, Float> getValue(String date) {
    // todo : date validation
    HashMap<String, Float> portfolioValueMap = new HashMap<>();
    int count = 0;
    for (Map.Entry<Stock, Integer> entry : this.stocks.entrySet()) {
      count += 1;
      Stock stock = entry.getKey();
      int quantity = entry.getValue();
      portfolioValueMap.put(stock.getTicker(), (stock.getPriceOnDate(date) * quantity));
      if ((count % 5) == 0) {
        try {
          Thread.sleep(60000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return portfolioValueMap;
  }

  public float getTotalComp(String date) {
    // todo : date validation
    float total = 0.00f;
    for (Map.Entry<String, Float> entry : this.getValue(date).entrySet()) {
      total += entry.getValue();
    }
    return total;
  }

  public String getName() {
    return name;
  }

  public void addStock(Stock stock, int quantity) {
    // TODO : validate ticker, quantity for neg, 0, fractional
    stocks.put(stock, quantity);
  }

  public HashMap<Stock, Integer> getStocks() {
    return stocks;
  }
}

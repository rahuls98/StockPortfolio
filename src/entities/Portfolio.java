package entities;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {

  private String name;
  private HashMap<Stock, Integer> stocks;

  private String lastPossibleValue;

  public Portfolio(String name) {
    this.name = name;
    this.stocks = new HashMap<>();
    lastPossibleValue = "0";
  }

  public HashMap<String, Float> getValue(String date) {
    HashMap<String, Float> portfolioValueMap = new HashMap<>();
    int count = 0;
    for (Map.Entry<Stock, Integer> stock : this.stocks.entrySet()) {
      count += 1;
      portfolioValueMap.put(stock.getKey().getTicker(), (stock.getKey().getPriceOnDate(date) * stock.getValue()));
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
    float total = 0.00f;
    for (Map.Entry<String, Float> pair : this.getValue(date).entrySet()) {
      total += pair.getValue();
    }
    return total;
  }

  public String getName() {
    return name;
  }

  public void addStock(Stock stock, int quantity) {
    stocks.put(stock, quantity);
  }

  public HashMap<Stock, Integer> getStocks() {
    return stocks;
  }
}

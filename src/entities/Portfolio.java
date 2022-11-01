package entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Description of class.
 */
public class Portfolio {

  private String name;
  private HashMap<Stock, Integer> stocks;
  private String lastPossibleValue;

  /**
   * Description of constructor.
   */
  public Portfolio(String name) {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Portfolio name cannot be empty!");
    }
    this.name = name;
    this.stocks = new HashMap<>();
    this.lastPossibleValue = "0";
  }

  /**
   * Description of method.
   *
   * @return desc.
   */
  public String getName() {
    return name;
  }

  /**
   * Description of method.
   *
   * @param stock desc.
   * @param quantity desc.
   */
  public void addStock(Stock stock, int quantity) {
    // TODO : validate ticker, quantity for neg, 0, fractional
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null!");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative!");
    }
    stocks.put(stock, quantity);
  }

  /**
   * Description of method.
   *
   * @return desc.
   */
  public HashMap<Stock, Integer> getStocks() {
    return stocks;
  }

  /**
   * Description of method.
   *
   * @return desc.
   */
  public HashMap<String, Integer> getStockQuantities() {
    HashMap<String, Integer> map = new HashMap<>();
    for (Map.Entry<Stock, Integer> entry : this.stocks.entrySet()) {
      map.put(entry.getKey().getTicker(), entry.getValue());
    }
    return map;
  }

  /**
   * Description of method.
   *
   * @param date desc.
   * @return desc.
   */
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

  /**
   * Description of method.
   *
   * @param date desc.
   * @return desc.
   */
  public float getTotalComp(String date) {
    // todo : date validation
    float total = 0.00f;
    for (Map.Entry<String, Float> entry : this.getValue(date).entrySet()) {
      total += entry.getValue();
    }
    return total;
  }
}

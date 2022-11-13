package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Represents a Portfolio having multiple stocks and their quantities.
 */
class Portfolio implements PortfolioInstanceModel {

  private String name;
  private final HashMap<Stock, Integer> stocks;

  /**
   * Returns an object of Portfolio.
   *
   * @param name Name of the portfolio
   */
  public Portfolio(String name) throws IllegalArgumentException {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Portfolio name cannot be empty!");
    }
    this.name = name;
    this.stocks = new HashMap<>();
  }

  /**
   * Retrieves the name of the portfolio.
   *
   * @return Portfolio name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the portfolio.
   *
   * @param name Name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Adds a new stock and its quantity to the portfolio.
   *
   * @param stock    Stock to add.
   * @param quantity Quantity of the stock.
   */
  public void addStock(Stock stock, int quantity) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null!");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative!");
    }
    stocks.put(stock, quantity);
  }

  /**
   * Returns the contents of the portfolio.
   *
   * @return Portfolio contents.
   */
  public HashMap<Stock, Integer> getStocks() {
    return stocks;
  }

  /**
   * Returns all the stocks in the portfolio and their quantities.
   *
   * @return Portfolio stocks and quantities.
   */
  public HashMap<String, Integer> getStockQuantities() {
    HashMap<String, Integer> map = new HashMap<>();
    for (Map.Entry<Stock, Integer> entry : this.stocks.entrySet()) {
      map.put(entry.getKey().getTicker(), entry.getValue());
    }
    return map;
  }

  /**
   * Retrieves the stocks of the portfolio and their values on a given date.
   *
   * @param date Date to retrieve values for.
   * @return Stocks their values on the given date.
   */
  public HashMap<String, Float> getValue(String date) {
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
   * Retrieves the total value of the portfolio on a given date.
   *
   * @param date Date to retrieve total value for.
   * @return Total value of the portfolio.
   */
  public float getTotalComp(String date) {
    float total = 0.00f;
    for (Map.Entry<String, Float> entry : this.getValue(date).entrySet()) {
      total += entry.getValue();
    }
    return total;
  }

  /**
   * Retrieves names of all stocks in the portfolio.
   *
   * @return Tickers of all stocks in portfolio.
   */
  public HashSet<String> getStockNames() {
    HashSet<String> map = new HashSet<>();
    for (Map.Entry<Stock, Integer> entry : this.stocks.entrySet()) {
      map.add(entry.getKey().getTicker());
    }
    return map;
  }

  // TODO : handle later with a new interface that extends old one
  @Override
  public Boolean placeOrder(Order o) {
    return null;
  }

  @Override
  public Float getTotalValue(HashMap<String, Float> values) {
    return null;
  }

  @Override
  public HashMap<String, Integer> getStockCompositionOnDate(LocalDate d) {
    return null;
  }

  @Override
  public float getCostBasis(LocalDate date) {
    return 0;
  }
}

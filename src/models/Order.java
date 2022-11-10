package models;

import java.time.LocalDate;
import java.util.HashMap;

public class Order {
  private Action action;
  private LocalDate date;
  private HashMap<String, Integer> stocks;
  private float commission;

  public Order(Action e, LocalDate d, float c) {
    this.action = e;
    this.date = d;
    this.stocks = new HashMap<>();
    this.commission = c;
  }

  public Action getAction() {
    return this.action;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public HashMap<String, Integer> getStocks() {
    return stocks;
  }

  public float getCommission() {
    return this.commission;
  }

  public void addStock(String stockTicker, int stockQuantity) {
    // TODO : handle multiple entries for same stock
    this.stocks.put(stockTicker, stockQuantity);
  }

  public void addStocks(HashMap<String, Integer> stocks) {
    // TODO : handle multiple entries for same stock
    this.stocks.putAll(stocks);
  }
}

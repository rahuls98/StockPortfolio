package models;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * This class represents an order pertaining to a set of stock transactions, and its associated
 * functionalities.
 */
public class Order {
  private Action action;
  private LocalDate date;
  private HashMap<String, Float> stocks;
  private float commission;

  /**
   * Constructs an object of an Order, which is of a particular Action type, has a corresponding
   * date, commission value, and a set of stock transactions.
   *
   * @param e Order action
   * @param d Order date
   * @param c Order commission
   */
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

  public HashMap<String, Float> getStocks() {
    return stocks;
  }

  public float getCommission() {
    return this.commission;
  }

  public void addStock(String stockTicker, float stockQuantity) {
    // TODO : handle multiple entries for same stock
    this.stocks.put(stockTicker, stockQuantity);
  }

  public void addStocks(HashMap<String, Float> stocks) {
    // TODO : handle multiple entries for same stock
    this.stocks.putAll(stocks);
  }
}

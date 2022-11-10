package models;

import java.time.LocalDate;
import java.util.HashMap;

public class Order {
  private Action action;
  private LocalDate date;
  private HashMap<String, Integer> stocks;
  private float commission;

  public Order(Action e, LocalDate d, HashMap<String, Integer> s, float c) {
    this.action = e;
    this.date = d;
    this.stocks = s;
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
}

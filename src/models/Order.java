package models;

import java.time.LocalDate;
import java.util.HashMap;

public class Order {
  private Action action;
  private LocalDate date;
  private HashMap<String, Integer> stocks;

  public Order(Action e, LocalDate d, HashMap<String, Integer> s) {
    this.action = e;
    this.date = d;
    this.stocks = s;
  }
}

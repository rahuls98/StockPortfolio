package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class newPortfolio implements PortfolioInstanceModel {
  private String name;

  private ArrayList<Order> orderBook;

  private HashMap<String, Stock> stocks;

  public newPortfolio(String name) {
    this.name = name;
    this.orderBook = new ArrayList<>();
    this.stocks = new HashMap<>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void addStock(Stock stock, int quantity) {

  }

  @Override
  public Boolean placeOrder(Order o) {
    if (o.getAction() == Action.BUY) {
      orderBook.add(o);
      return true;
    } else {
      HashMap<String, Integer> composition = this.getStockQuantities();
      for (Map.Entry<String, Integer> entry : o.getStocks().entrySet()) {
        if (!(composition.containsKey(entry.getKey()))) {
          return false;
        }
        if (composition.get(entry.getKey()) < entry.getValue()) {
          return false;
        }
      }
      orderBook.add(o);
      return true;
    }
  }

//  public void buy(Order o) {
//    //TODO: Validations
//    orderBook.add(o);
//  }
//
//  public Boolean sell(Order o) {
//    HashMap<String, Integer> composition = this.getStockQuantities();
//    for (Map.Entry<String, Integer> entry : o.getStocks().entrySet()) {
//      if (!(composition.containsKey(entry.getKey()))) {
//        return false;
//      }
//      if (composition.get(entry.getKey()) < entry.getValue()) {
//        return false;
//      }
//    }
//    orderBook.add(o);
//    return true;
//  }

  public float getCostBasis(LocalDate date) {
    ArrayList<Order> ordBook = this.getOrderBookOnDate(date);
    float costBasis = 0.00f;
    for(int i = 0; i < ordBook.size(); i++) {
      costBasis += ordBook.get(i).getCommission();

    }

    return costBasis;
  }

  public Boolean isValidDate(LocalDate date) {
    if (this.orderBook.isEmpty()) {
      return true;
    }
    return (this.orderBook.get(this.orderBook.size() - 1).getDate().compareTo(date) <= 0);
  }

  @Override
  public HashMap<Stock, Integer> getStocks() {
    return null;
  }

  /**
   * Returns the composition of the Portfolio
   *
   * @return composition of portfolio currently
   */
  @Override
  public HashMap<String, Integer> getStockQuantities() {
    return getComposition(this.orderBook);
  }

  public HashMap<String, Integer> getStockCompositionOnDate(LocalDate d) {
    return getComposition(this.getOrderBookOnDate(d));
  }

  public ArrayList<Order> getOrderBookOnDate(LocalDate date) {
    ArrayList<Order> ordBook = new ArrayList<>();
    for (int i = 0; i < this.orderBook.size(); i++) {
      if (date.compareTo(this.orderBook.get(i).getDate()) >= 0) {
        ordBook.add(this.orderBook.get(i));
      }
    }
    return ordBook;
  }

  public HashMap<String, Integer> getComposition(ArrayList<Order> orders) {
    HashMap<String, Integer> composition = new HashMap<>();
    //Add stocks
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getAction() == Action.BUY) {
        for (Map.Entry<String, Integer> entry : orders.get(i).getStocks().entrySet()) {
          if (!(composition.containsKey(entry.getKey()))) {
            composition.put(entry.getKey(), 0);
          }
          composition.put(entry.getKey(), composition.get(entry.getKey()) + 1);
        }
      }
    }
    //Subtract sell stocks
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getAction() == Action.SELL) {
        for (Map.Entry<String, Integer> entry : orders.get(i).getStocks().entrySet()) {
          composition.put(entry.getKey(), composition.get(entry.getKey()) - 1);
          if (composition.get(entry.getKey()) == 0) {
            composition.remove(entry.getKey());
          }
        }
      }
    }
    return composition;
  }

  @Override
  public HashMap<String, Float> getValue(String date) {
    HashMap<String, Float> values = new HashMap<>();
    HashMap<String, Integer> comp = this.getStockCompositionOnDate(LocalDate.parse(date));
    for (Map.Entry<String, Integer> entry : comp.entrySet()) {
      if (!(values.containsKey(entry.getKey()))) {
        values.put(entry.getKey(), 0f);
      }
      if (!(this.stocks.containsKey(entry.getKey()))) {
        this.stocks.put(entry.getKey(), new Stock(entry.getKey()));
      }
      values.put(entry.getKey(),
              (values.get(entry.getKey()) +
                      (entry.getValue() *
                              this.stocks.get(entry.getKey()).getPriceOnDate(date))));
    }
    return values;
  }

  @Override
  public float getTotalComp(String date) {
    float total = 0.00f;
    for (Map.Entry<String, Float> entry : this.getValue(date).entrySet()) {
      total += entry.getValue();
    }
    return total;
  }

  @Override
  public HashSet<String> getStockNames() {
    return null;
  }

  @Override
  public String toString() {
    return this.name;
  }
}

package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class newPortfolio implements PortfolioInstanceModel {
  private String name;

  private ArrayList<Order> orderBook;

  //TODO: REMOVE THIS!!!!
  public newPortfolio(String name, ArrayList<Order> ordBook) {
    this.name = name;
    this.orderBook = ordBook;
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

  public void buy(LocalDate date, HashMap<String, Integer> stocks) {
    //TODO: Validations
    orderBook.add(new Order(Action.BUY, date, stocks));
  }

  public void sell(LocalDate date, HashMap<String, Integer> stocks) {
    HashMap<String, Integer> composition = this.getStockQuantities();
    for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
      if (!(composition.containsKey(entry.getKey()))) {
        //Silently Fail, stock not in portfolio
        return;
      }
      if (composition.get(entry.getKey()) < entry.getValue()) {
        //Silently fail, Quantity of sell action not in portfolio
        return;
      }
    }
    orderBook.add(new Order(Action.SELL, date, stocks));
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
      if (date.compareTo(this.orderBook.get(i).getDate()) < 0) {
        break;
      }
      ordBook.add(this.orderBook.get(i));
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
    return null;
  }

  @Override
  public float getTotalComp(String date) {
    return 0;
  }

  @Override
  public HashSet<String> getStockNames() {
    return null;
  }
}

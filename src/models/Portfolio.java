package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Represents a Portfolio which can be various PortfolioTypes, having multiple stocks and
 * their quantities.
 */
public class Portfolio implements PortfolioInstanceModel {
  private String name;
  private ArrayList<Order> orderBook;

  private HashMap<String, Stock> stocks;

  private PortfolioType type;

  /**
   * Returns an object of Portfolio which will have a name, a PortfolioType, and a set of orders
   * with stock transactions.
   *
   * @param name          Name of the portfolio
   * @param type          Type of the portfolio according to PortfolioType
   * @param initialOrders Inital orders to add to the portfolio
   */
  public Portfolio(String name, PortfolioType type, List<Order> initialOrders) {
    this.name = name;
    this.type = type;
    this.orderBook = new ArrayList<>();
    this.orderBook.addAll(initialOrders);
    this.stocks = new HashMap<>();
  }

  @Override
  public PortfolioType getType() {
    return this.type;
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
  public Boolean placeOrder(Order o) {
    if (this.type == PortfolioType.INFLEXIBLE) {
      return false;
    }
    if (o.getAction() == Action.BUY) {
      orderBook.add(o);
    } else {
      //Check Date greater than earliest
      if (orderBook.isEmpty()) {
        return false;
      }
      if (o.getDate().compareTo(this.getEarliestDate()) < 0) {
        return false;
      }
      orderBook.add(o);
      if (!(this.isOrderBookValid())) {
        orderBook.remove(orderBook.size() - 1);
        return false;
      }
    }
    return true;
  }

  private LocalDate getEarliestDate() {
    LocalDate earliest = this.orderBook.get(0).getDate();
    for (int i = 0; i < this.orderBook.size(); i++) {
      if (earliest.compareTo(this.orderBook.get(i).getDate()) > 0) {
        earliest = this.orderBook.get(i).getDate();
      }
    }
    return earliest;
  }

  private Boolean isOrderBookValid() {
    HashMap<String, Integer> comp = this.getStockQuantities();
    for (Map.Entry<String, Integer> stock : comp.entrySet()) {
      if (stock.getValue() < 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public float getCostBasis(LocalDate date) {
    ArrayList<Order> ordBook = this.getOrderBookOnDate(date);
    float costBasis = 0.00f;
    for (int i = 0; i < ordBook.size(); i++) {
      costBasis += ordBook.get(i).getCommission();
      if (ordBook.get(i).getAction() == Action.BUY) {
        for (Map.Entry<String, Integer> stock : ordBook.get(i).getStocks().entrySet()) {
          if (!(this.stocks.containsKey(stock.getKey()))) {
            this.stocks.put(stock.getKey(), new Stock(stock.getKey()));
          }
          //The cost of the stock on the purchase date.
          costBasis += (stock.getValue() * this.stocks.get(stock.getKey())
                  .getPriceOnDate(ordBook.get(i).getDate().toString()));
        }
      }
    }
    return costBasis;
  }

  @Override
  public HashMap<String, Integer> getStockQuantities() {
    return getComposition(this.orderBook);
  }

  @Override
  public HashMap<String, Integer> getStockCompositionOnDate(LocalDate d) {
    return getComposition(this.getOrderBookOnDate(d));
  }

  private ArrayList<Order> getOrderBookOnDate(LocalDate date) {
    ArrayList<Order> ordBook = new ArrayList<>();
    for (int i = 0; i < this.orderBook.size(); i++) {
      if (date.compareTo(this.orderBook.get(i).getDate()) >= 0) {
        ordBook.add(this.orderBook.get(i));
      }
    }
    return ordBook;
  }

  private HashMap<String, Integer> getComposition(ArrayList<Order> orders) {
    HashMap<String, Integer> composition = new HashMap<>();
    //Add stocks
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getAction() == Action.BUY) {
        for (Map.Entry<String, Integer> entry : orders.get(i).getStocks().entrySet()) {
          if (!(composition.containsKey(entry.getKey()))) {
            composition.put(entry.getKey(), 0);
          }
          composition.put(entry.getKey(), composition.get(entry.getKey()) + entry.getValue());
        }
      }
    }
    //Subtract sell stocks
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getAction() == Action.SELL) {
        for (Map.Entry<String, Integer> entry : orders.get(i).getStocks().entrySet()) {
          if (!(composition.containsKey(entry.getKey()))) {
            composition.put(entry.getKey(), 0);
          }
          composition.put(entry.getKey(), composition.get(entry.getKey()) - entry.getValue());
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
      values.put(entry.getKey(), (values.get(entry.getKey()) + (entry.getValue()
              * this.stocks.get(entry.getKey()).getPriceOnDate(date))));
    }
    return values;
  }

  @Override
  public HashSet<String> getStockNames() {
    return null;
  }

  @Override
  public Float getTotalValue(HashMap<String, Float> comp) {
    float total = 0.00f;
    for (Map.Entry<String, Float> entry : comp.entrySet()) {
      total += entry.getValue();
    }
    return total;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public ArrayList<Order> getOrderBook() {
    return this.orderBook;
  }
}

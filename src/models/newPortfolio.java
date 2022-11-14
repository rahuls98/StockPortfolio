package models;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
    return;
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
          costBasis += (stock.getValue() * this.stocks.get(stock.getKey()).getPriceOnDate(ordBook.get(i).getDate().toString()));
        }
      }
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

  @Override
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
//          composition.put(entry.getKey(), composition.get(entry.getKey()) + 1);
          composition.put(entry.getKey(), composition.get(entry.getKey()) + entry.getValue());
        }
      }
    }
    //Subtract sell stocks
    for (int i = 0; i < orders.size(); i++) {
      if (orders.get(i).getAction() == Action.SELL) {
        for (Map.Entry<String, Integer> entry : orders.get(i).getStocks().entrySet()) {
          composition.put(entry.getKey(), composition.get(entry.getKey()) - entry.getValue());
          if (composition.get(entry.getKey()) <= 0) {
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
    return 0;
  }


  public Float getTotalValue(HashMap<String, Float> comp) {
    float total = 0.00f;
    for (Map.Entry<String, Float> entry : comp.entrySet()) {
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

  private static int findGCD(int a, int b){
    if (b == 0)
      return a;
    return findGCD(b, a%b);
  }

  @Override
  public void dateRangeSplitter(String date1, String date2) {
    try {
      LocalDate ld1 = LocalDate.parse(date1);
      LocalDate ld2 = LocalDate.parse(date2);
      long diff = (ld2.toEpochDay() - ld1.toEpochDay());
      if (diff < 4) {
        System.out.println("Error!");
        return;
      }
      int interval = 4;
      for (int k = 30; k >= 4; k--) {
        if (diff % k == 0) {
          interval = k;
          break;
        }
      }
      diff = diff / interval;
      int i;
      int j = 0;
      LocalDate date = null;
      ArrayList<Float> values = new ArrayList<>();
      TreeMap<String, Float> dateMapper = new TreeMap<>();
      for (i = 0; i < interval; i++) {
        j = 0;
        date = LocalDate.ofEpochDay(ld1.toEpochDay() + (diff * i));
        if (date.isAfter(ld2)) {
          break;
        }
        boolean flag = true;
        while (true) {
          try {
            this.getValue(date.toString());
            break;
          } catch (NullPointerException e) {
            j++;
            date = LocalDate.ofEpochDay(ld1.toEpochDay() + j + (diff * i));
            if (date.isAfter(ld2)) {
              flag = false;
              break;
            }
          }
        }
        if (flag) {
          HashMap<String, Float> hm = this.getValue(date.toString());
          float total = 0;
          for (Map.Entry<String, Float> mapEntry: hm.entrySet()) {
            total += mapEntry.getValue();
          }
          values.add(total);
          dateMapper.put(date.toString(), total);
        } else {
          break;
        }
      }
      date = LocalDate.ofEpochDay(ld1.toEpochDay() + j + (diff * i));
      if (!date.isAfter(ld2)) {
        HashMap<String, Float> hm = this.getValue(date.toString());
        float total = 0;
        for (Map.Entry<String, Float> mapEntry: hm.entrySet()) {
          total += mapEntry.getValue();
        }
        values.add(total);
        dateMapper.put(date.toString(), total);
      }
      //TODO: Fix this
      float min = Integer.MAX_VALUE;
      for (float val : values) {
        if (val == 0) {
          continue;
        }
        if (val < min) {
          min = val;
        }
      }
      System.out.println("Scale: * = $" + (int)min);
      for (Map.Entry<String, Float> mapEntry : dateMapper.entrySet()) {
        // LocalDate ld = LocalDate.parse(mapEntry.getKey());
        // System.out.print(ld.getMonth().toString().substring(0,3) + ", " + ld.getYear() + ": ");
        System.out.print(mapEntry.getKey() + ": ");
        for (int b = 0; b < (int)(mapEntry.getValue() / min); b++) {
          System.out.print("*");
        }
        System.out.println();
      }
    } catch (DateTimeException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public ArrayList<Order> getOrderBook() {
    return this.orderBook;
  }
}

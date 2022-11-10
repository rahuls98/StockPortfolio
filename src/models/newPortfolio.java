package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    orderBook.add(new Order(Action.BUY, date, stocks));
  }

  public void sell(LocalDate date, HashMap<String, Integer> stocks) {
    //TODO: Validation to check if stock exists
    orderBook.add(new Order(Action.SELL, date, stocks));
  }

  public Boolean isValidDate(LocalDate date) {
    //TODO: Check if date is in chronological order
    return null;
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
    return null;
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

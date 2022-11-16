package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface PortfolioInstanceModel {

  PortfolioType getType();

  /**
   * Retrieves the name of the portfolio.
   *
   * @return Portfolio name.
   */
  public String getName();

  /**
   * Sets the name of the portfolio.
   *
   * @param name Name to set.
   */
  public void setName(String name);



  /**
   * Returns all the stocks in the portfolio and their quantities.
   *
   * @return Portfolio stocks and quantities.
   */
  public HashMap<String, Integer> getStockQuantities();

  /**
   * Retrieves the stocks of the portfolio and their values on a given date.
   *
   * @param date Date to retrieve values for.
   * @return Stocks their values on the given date.
   */

  public HashMap<String, Float> getValue(String date);



  /**
   * Retrieves names of all stocks in the portfolio.
   *
   * @return Tickers of all stocks in portfolio.
   */
  public HashSet<String> getStockNames();

  public Boolean placeOrder(Order o);

  public Float getTotalValue(HashMap<String, Float> values);
  public HashMap<String, Integer> getStockCompositionOnDate(LocalDate d);
  public float getCostBasis(LocalDate date);
  ArrayList<Order> getOrderBook();
}

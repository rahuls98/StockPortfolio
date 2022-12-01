package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents the methods supported by an instance of a Portfolio.
 */
public interface PortfolioInstanceModel {
  /**
   * Returns the type of portfolio.
   *
   * @return an Enum of PortfolioType.
   */
  PortfolioType getType();

  /**
   * Retrieves the name of the portfolio.
   *
   * @return Portfolio name.
   */
  String getName();

  /**
   * Sets the name of the portfolio.
   *
   * @param name Name to set.
   */
  void setName(String name);

  /**
   * Returns the composition of the Portfolio.
   *
   * @return Current composition of the portfolio represented as stocks mapped to their quantities.
   */
  HashMap<String, Float> getStockQuantities();

  /**
   * Retrieves the stocks of the portfolio and their values on a given date.
   *
   * @param date Date to retrieve values for.
   * @return Stocks their values on the given date.
   */

  HashMap<String, Float> getValue(String date);

  /**
   * Retrieves names of all stocks in the portfolio.
   *
   * @return Tickers of all stocks in portfolio.
   */
  HashSet<String> getStockNames();

  /**
   * Adds an order to the current portfolio.
   *
   * @param o Order to add.
   * @return true if the order was added successfully, false if not.
   */
  Boolean placeOrder(Order o);

  /**
   * Total value of the given stock values.
   *
   * @param values Values to calculate total for.
   * @return Total value.
   */
  Float getTotalValue(HashMap<String, Float> values);

  /**
   * Returns the composition of the portfolio on a given date.
   *
   * @param d Date to get the composition for.
   * @return Composition of the portfolio on the given date.
   */
  HashMap<String, Float> getStockCompositionOnDate(LocalDate d);

  /**
   * Returns the cost basis of the portfolio on a given date.
   *
   * @param date Date to get the cost basis for.
   * @return Cost basis of the portfolio on the given date.
   */
  float getCostBasis(LocalDate date);

  /**
   * Returns a list of all the orders in the current portfolio.
   *
   * @return Orders in the current portfolio.
   */
  ArrayList<Order> getOrderBook();
}

package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface PortfolioInstanceModel {

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
   * Adds a new stock and its quantity to the portfolio.
   *
   * @param stock    Stock to add.
   * @param quantity Quantity of the stock.
   */
  public void addStock(Stock stock, int quantity);

  /**
   * Returns the contents of the portfolio.
   *
   * @return Portfolio contents.
   */
  public HashMap<Stock, Integer> getStocks();

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
   * Retrieves the total value of the portfolio on a given date.
   *
   * @param date Date to retrieve total value for.
   * @return Total value of the portfolio.
   */
  public float getTotalComp(String date);

  /**
   * Retrieves names of all stocks in the portfolio.
   *
   * @return Tickers of all stocks in portfolio.
   */
  public HashSet<String> getStockNames();
}

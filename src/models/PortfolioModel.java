package models;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents classes that manage stock portfolios and all their associated functionalities.
 */
public interface PortfolioModel {

  /**
   * Add a portfolio to the current user.
   *
   * @param portfolioName Name of the portfolio to add.
   */
  void addPortfolio(String portfolioName);

  //
//  @Override
//  public void addPortfolio(String portfolioName) {
//    this.user.addPortfolio(new Portfolio(portfolioName));
//  }
  //TODO: It takes, type, Hashmap of stock & quantity.
  void addFlexiblePortfolio(String portfolioName);

  //TODO: It takes, type, Hashmap of stock & quantity.
  void addInflexiblePortfolio(String portfolioName, HashMap<String, Integer> stocks);

  /**
   * Finds and retrieves a portfolio of the current user, searching by the portfolio name.
   *
   * @param portfolioName Name of the portfolio to retrieve.
   * @return Portfolio object with all of its stocks and quantities.
   */
  PortfolioInstanceModel getPortfolio(String portfolioName);

  /**
   * Retrieves the names of all portfolios of the current user.
   *
   * @return Names of all portfolios.
   */
  String[] getPortfolios();

  String[] getFlexiblePortfolios();

  String[] getInflexiblePortfolios();

  /**
   * Retrieves all the values of all stocks in a portfolio on a particular date.
   *
   * @param portfolioName Name of the portfolio.
   * @param date          Date to get the values for.
   * @return All stocks and their values on the given date.
   */
  HashMap<String, Float> getPortfolioValues(String portfolioName, String date);

  /**
   * Retrieves the total value of a portfolio on a particular date.
   *
   * @param portfolioName Name of the portfolio.
   * @param date          Date to get the total value for.
   * @return Total value of the portfolio on the given date.
   */
  Float getPortfolioTotal(String portfolioName, String date);

  /**
   * Checks if a given stock ticker symbol is valid or not.
   *
   * @param ticker Ticker symbol of the stock.
   * @return True if symbol is a valid ticker, False if not.
   */
  Boolean isValidTicker(String ticker);

  /**
   * Loads a user portfolio from a given portfolio XML file.
   *
   * @param pathToXml Path to the XML file containing the portfolio information.
   * @return Loaded portfolio and its contents.
   */
  Portfolio_old loadPortfolioFromXml(String pathToXml);

  /**
   * Loads the name of a portfolio from a given portfolio XML file.
   *
   * @param pathToXml Path to the XML file containing the portfolio information.
   * @return Name of the portfolio.
   */
  String loadPortfolioNameFromXML(String pathToXml);

  /**
   * Checks if a given date is valid or not.
   *
   * @param date Date string.
   * @return True if date is valid, False if not.
   */
  boolean isValidDate(String date);

  /**
   * Adds a stock and its quantity to a given portfolio.
   *
   * @param portfolioName Name of the portfolio to add the stock to.
   * @param stockTicker   Ticker symbol of the stock to add.
   * @param stockQuantity Quantity of the stock to add.
   */
  void addStock(String portfolioName, String stockTicker, int stockQuantity);

  /**
   * Adds a portfolio to the current user.
   *
   * @param portfolioOld     Portfolio to add.
   * @param portfolioName Name of the portfolio to add.
   */
  void addPortfolioToUser(Portfolio_old portfolioOld, String portfolioName);

  /**
   * Persists data to storage.
   */
  void persist();

  /**
   * Retrieves a set of ticker symbols of all stocks in a given portfolio.
   *
   * @param portfolioName Name of the portfolio.
   * @return Set of stock ticker symbols.
   */
  HashSet<String> getStockTickersInPortfolio(String portfolioName);

  /**
   * Retrieves all stocks and their quantities in a given portfolio.
   *
   * @param portfolioName Name of the portfolio.
   * @return Stocks and their quantities.
   */
  HashMap<String, Integer> getStockQuantitiesInPortfolio(String portfolioName, String date);

  /**
   * Creates an Order
   * @param date
   * @param action
   * @param c
   * @param stocks
   * @return
   */
  public Order createOrder(String date, String action, float c, HashMap<String, Integer> stocks);

  /**
   * Adds order to portfolio
   * @param portfolio
   * @param o
   */
  public void addOrderToPortfolio(String portfolio, Order o);

  public Float getCostBasis(String portfolioName, String date);

  public void getPerformance(String portfolioName, String date1, String date2);
}

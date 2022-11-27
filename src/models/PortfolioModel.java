package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

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

  /**
   * Add a portfolio to the current user.
   *
   * @param portfolio     portfolio object.
   * @param portfolioName portfolio name.
   */
  void addPortfolioToUser(Portfolio portfolio, String portfolioName);

  /**
   * Adds a new flexible portfolios.
   *
   * @param portfolioName name of portfolio.
   */
  void addFlexiblePortfolio(String portfolioName);

  /**
   * Add a new inflexible portfolio.
   *
   * @param portfolioName portfolio name.
   * @param stocks        stocks to add.
   */
  void addInflexiblePortfolio(String portfolioName, HashMap<String, Integer> stocks);


  /**
   * Retrieves the names of all portfolios of the current user.
   *
   * @return Names of all portfolios.
   */
  String[] getPortfolios();

  /**
   * returns the list of flexible portfolios.
   *
   * @return flexible portfolios.
   */
  String[] getFlexiblePortfolios();

  /**
   * returns inflexible portfolios.
   *
   * @return inflexible portfolios.
   */

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
   */
  void loadPortfolioFromXml(String pathToXml);

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
   * Adds an order to the Portfolio.
   *
   * @param portfolio to add the order to.
   * @param date      date of the order.
   * @param action    either BUY or SELL.
   * @param c         commission for the order.
   * @param stocks    Stocks in the order.
   * @return True if successful, false otherwise.
   */
  Boolean addOrderToPortfolioFromController(String portfolio, String date, String action, float c,
                                            HashMap<String, Integer> stocks);

  /**
   * Returns the cost basis of portfolio upto date.
   *
   * @param portfolioName the portfolio to return cost basis.
   * @param date          date up to which cost basis is returned.
   * @return the cost basis.
   */
  Float getCostBasis(String portfolioName, String date);

  /**
   * Returns the performance of the portfolio as a Treemap.
   *
   * @param portfolioName name of the portfolio.
   * @param date1         lower date for performance.
   * @param date2         upper date for performance.
   * @return Treemap of dates, floats.
   */
  TreeMap<String, Float> getPerformanceValues(String portfolioName, String date1,
                                              String date2);

  /**
   * Returns the scale to be considered for performance.
   *
   * @param values Treemap of dates to values.
   * @return scale as a float.
   */
  Float getScale(TreeMap<String, Float> values);
}

package models;

import java.util.HashMap;

/**
 * Represents classes that manage stock portfolios and all their associated functionalities.
 */
public interface PortfolioModel {

  /**
   * Update a user by adding a new portfolio.
   *
   * @param user User to whom the portfolio should be added to.
   */
  void addPortfolio(User user);

  /**
   * Finds and retrieves a portfolio of a user by the name of the portfolio.
   *
   * @param portfolioName Name of the portfolio to retrieve.
   * @return Portfolio object with all of its stocks and quantities.
   */
  Portfolio getPortfolio(String portfolioName);

  /**
   * Retrieves the names of all portfolios of a user.
   *
   * @return Names of all portfolios of a user.
   */
  String[] getPortfolios();

  /**
   * Retrieves all the values of all stocks in a portfolio on a particular date.
   *
   * @param portfolioName Name of the portfolio.
   * @param date Date to get the values for.
   * @return All stocks in the portfolio and their values on the given date.
   */
  HashMap<String, Float> getPortfolioValues(String portfolioName, String date);

  /**
   * Retrieves the total value of a portfolio on a particular date.
   *
   * @param portfolioName Name of the portfolio.
   * @param date Date to get the total value for.
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
   * Loads a user portfolio from a given XML file.
   *
   * @param pathToXml Path to the XML file containing the portfolio information.
   * @return Loaded portfolio and its contents.
   */
  Portfolio loadPortfolioFromXml(String pathToXml);

  /**
   * Checks if a given date is valid or not.
   *
   * @param date Date string.
   * @return True if date is valid, False if not.
   */
  boolean isValidDate(String date);
}

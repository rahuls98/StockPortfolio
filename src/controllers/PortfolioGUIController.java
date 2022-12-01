package controllers;


import java.io.File;
import java.util.HashMap;

/**
 * Represents classes that control the program flow for GUI's based on user-interaction.
 */
public interface PortfolioGUIController {

  /**
   * Method used to exit the application.
   */
  void exitProgram();

  /**
   * Method used to go to the create portfolio screen.
   */
  void goToCreatePortfolio();

  /**
   * Method used to go to the home screen.
   */
  void goToHome();

  /**
   * Method used to load a file from file system.
   *
   * @param file Target file object.
   */
  void getFile(File file);

  /**
   * Method used to go to the display portfolio composition screen.
   */
  void goToDisplayPortfoliosComp();

  /**
   * Method used to display portfolio composition.
   *
   * @param pName Name of the portfolio.
   * @param date  Date to display the composition for.
   */
  void displayPortfolioComposition(String pName, String date);

  /**
   * Method used to go to the display value screen.
   */
  void goToDisplayValue();

  /**
   * Method used to display the value of the portfolio.
   *
   * @param pName Name of the portfolio.
   * @param date  Date to display the composition for.
   */
  void displayPortfolioValue(String pName, String date);

  /**
   * Method used to create a portfolio.
   *
   * @param pName Name of the portfolio.
   */
  void createNewPortfolio(String pName);

  /**
   * Method used to go to the manual portfolio creations screen.
   */
  void goToCreatePortfolioManually();

  /**
   * Method used to go to the create order screen.
   */
  void goToCreateOrder();

  /**
   * Method used to create an order.
   *
   * @param portfolio Name of the portfolio.
   * @param date      Date to create the order for.
   * @param action    Action of the order.
   * @param c         Order commission.
   * @param stocks    Stocks to add in the order.
   */
  void createOrder(String portfolio, String date, String action, String c,
                   HashMap<String, String> stocks);

  /**
   * Method used to go to the investment plan screen.
   */
  void goToInvestmentPlanScreen();

  /**
   * Method used to go to the investment by percentage plan screen.
   *
   * @param portfolioName Name of the portfolio
   */
  void goToInvestByPercentageScreen(String portfolioName);

  /**
   * Method used to go to the portfolio performance screen.
   */
  void goToPortfolioPerformance();

  /**
   * Method used to perform the investment by percentage strategy.
   *
   * @param portfolioName Name of the portfolio.
   * @param amount        Amount to invest.
   * @param date          Date of investment.
   * @param stocks        Stocks to invest.
   * @param commission    Commission of the order.
   */
  void investByPercentage(String portfolioName,
                          String amount,
                          String date,
                          HashMap<String, String> stocks,
                          String commission);

  /**
   * Method used to perform the investment by SIP strategy.
   *
   * @param portfolioName Name of the portfolio.
   * @param amount        Amount to invest using strategy.
   * @param startDate     Starting date for the investment period.
   * @param endDate       Ending date for the investment period.
   * @param interval      Day intervals to perform investments.
   * @param stocks        Portfolio of stocks to invest using strategy.
   * @param commission    Commission for each order.
   */
  void investBySIP(String portfolioName,
                   String amount,
                   String startDate,
                   String endDate,
                   String interval,
                   HashMap<String, String> stocks,
                   String commission);

  /**
   * Method used to go to the invest SIP screen.
   *
   * @param portfolioName Name of the portfolio.
   */
  void goToInvestSipScreen(String portfolioName);

  /**
   * Method used to go to the cost basis screen.
   */
  void goToCostBasis();

  /**
   * Method used to get the cost basis of a portfolio.
   *
   * @param portfolioName Name of the portfolio.
   * @param date          Date to get the cost basis for.
   */
  void getCostBasis(String portfolioName, String date);

  /**
   * Method used to display the performance of a portfolio.
   *
   * @param portfolioName Name of the portfolio.
   * @param startDate     Starting date for the performance period.
   * @param endDate       Ending date for the performance period.
   */
  void displayPortfolioPerformance(String portfolioName,
                                   String startDate,
                                   String endDate);
}

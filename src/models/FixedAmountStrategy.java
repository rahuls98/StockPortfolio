package models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a class that executes a weighted investment strategy.
 */
public class FixedAmountStrategy<T> implements PortfolioOperation<T> {

  private final String portfolioName;
  private final float investmentAmount;
  private final LocalDate date;
  private final HashMap<String, Float> stocks;
  private final float commission;

  /**
   * Returns an object of the FixedAmountStrategy operation class, which can be used to execute
   * the weighted investment strategy.
   *
   * @param portfolioName Name of the portfolio.
   * @param investmentAmount Amount to invest using strategy.
   * @param date Date for executing the investment.
   * @param stocks Portfolio of stocks to invest using strategy.
   * @param commission Commission for each order.
   */
  public FixedAmountStrategy(String portfolioName, float investmentAmount,
                             LocalDate date, HashMap<String, Float> stocks, float commission)
          throws IllegalArgumentException {
    if (portfolioName == null || portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio required to apply strategy!");
    }
    if (investmentAmount <= 0) {
      throw new IllegalArgumentException("Investment amount should be a positive non-zero value!");
    }
    if (stocks.size() == 0) {
      throw new IllegalArgumentException("No stocks!");
    }
    float total = 0;
    for (Map.Entry<String, Float> stocksObj : stocks.entrySet()) {
      total += stocksObj.getValue();
    }
    if (total != 100) {
      throw new IllegalArgumentException("Weights don't add upto 100% !");
    }
    if (commission < 0) {
      throw new IllegalArgumentException("Commission cannot be negative!");
    }
    this.portfolioName = portfolioName;
    this.investmentAmount = investmentAmount;
    this.date = date;
    this.stocks = stocks;
    this.commission = commission;
  }

  @Override
  public T operate(PortfolioModel model) {
    Stock stock;
    float stockInvestment;
    float stockQuantity;
    HashMap<String, Float> stocksMap = new HashMap<>();
    for (Map.Entry<String, Float> stockObject : stocks.entrySet()) {
      stock = new Stock(stockObject.getKey());
      stockInvestment = (stockObject.getValue() / 100) * investmentAmount;
      stockQuantity = stockInvestment / stock.getPriceOnDate(date.toString());
      stocksMap.put(stockObject.getKey(), stockQuantity);
    }
    model.createOrderInPortfolio(this.portfolioName, this.date.toString(), "BUY",
            this.commission, stocksMap);
    return null;
  }
}

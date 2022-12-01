package models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FixedAmountStrategy<T> implements PortfolioOperation<T> {

  private final String portfolioName;
  private final float investmentAmount;
  private final LocalDate date;
  private final HashMap<String, Float> stocks;
  private final float commission;

  public FixedAmountStrategy(String portfolioName, float investmentAmount,
                             LocalDate date, HashMap<String, Float> stocks, float commission) {
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
    model.addOrderToPortfolioFromController(this.portfolioName, this.date.toString(), "BUY",
            this.commission, stocksMap);
    return null;
  }
}

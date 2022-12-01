package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class FixedAmountStrategyTest {
  private String portfolioName;
  private int investmentAmount;
  private float investmentCommission;
  private PortfolioModel model;
  private PortfolioOperation<Void> fas;

  @Before
  public void setup() {
    String userName = "test_user";
    this.portfolioName = "test_portfolio";
    this.model = null;
    try {
      model = new PortfolioModelImpl(userName);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    model.addFlexiblePortfolio(portfolioName);
    HashMap<String, Float> stocks = new HashMap<>();
    stocks.put("META", 40f);
    stocks.put("AAPL", 20f);
    stocks.put("NFLX", 30f);
    stocks.put("GOOG", 10f);
    this.investmentAmount = 2000;
    this.investmentCommission = 5.6f;
    this.fas = new FixedAmountStrategy<>(portfolioName, this.investmentAmount,
            LocalDate.parse("2022-11-10"), stocks, this.investmentCommission);
    model.accept(this.fas);
  }

  @Test
  public void testInvestment(){
    HashSet<String> hs = new HashSet<>();
    hs.add("META");
    hs.add("AAPL");
    hs.add("NFLX");
    hs.add("GOOG");
    HashMap<String, Float> stockQuantities =
            this.model.getStockQuantitiesInPortfolio(this.portfolioName, "2022-11-10");
    assertEquals(4, stockQuantities.size());
  }

  @Test
  public void testInvestmentValue() {
    float actualValue = model.getPortfolioTotal(portfolioName, "2022-11-10");
    assertEquals(this.investmentAmount, actualValue, 0.0);
  }

  @Test
  public void testInvestmentCostBasis() {
    float actualCostBasis = model.getCostBasis(portfolioName, "2022-11-10");
    assertEquals(this.investmentAmount + this.investmentCommission, actualCostBasis, 0.0);
  }
}
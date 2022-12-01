package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;

public class DollarCostAveragingTest {

  private String portfolioName;
  private int investmentAmount;
  private float investmentCommission;
  private PortfolioModel model;
  private PortfolioOperation<Void> dca;

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
    this.dca = new DollarCostAveraging<>(portfolioName, this.investmentAmount,
            LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
            stocks, this.investmentCommission);
    this.dca.operate(model);
  }

  @Test
  public void testInvestmentValue() {
    float actualValue1 = model.getPortfolioTotal(portfolioName, "2022-09-07");
    assertEquals(this.investmentAmount, actualValue1, 0.0f);
  }

  @Test
  public void testInvestmentCostBasis() {
    float actualCostBasis = model.getCostBasis(portfolioName, "2022-09-07");
    assertEquals(this.investmentAmount + this.investmentCommission, actualCostBasis, 0.0);
  }
}
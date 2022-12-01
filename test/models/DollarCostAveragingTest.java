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
  }

  @Test
  public void testInvestmentValue() {
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
    model.accept(this.dca);
    float actualValue1 = model.getPortfolioTotal(portfolioName, "2022-09-07");
    float actualValue2 = model.getPortfolioTotal(portfolioName, "2022-09-19");
    float actualValue3 = model.getPortfolioTotal(portfolioName, "2022-09-30");
    float actualValue4 = model.getPortfolioTotal(portfolioName, "2022-10-07");
    assertEquals(this.investmentAmount, actualValue1, 0.0f);
    assertEquals(1960, (int)(actualValue2));
    assertEquals(1822, (int)(actualValue3));
    assertEquals(3794, (int)(actualValue4));
  }

  @Test
  public void testInvestmentCostBasis() {
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
    model.accept(this.dca);
    float actualCostBasis1 = model.getCostBasis(portfolioName, "2022-09-07");
    float actualCostBasis2 = model.getCostBasis(portfolioName, "2022-09-18");
    float actualCostBasis3 = model.getCostBasis(portfolioName, "2022-09-30");
    float actualCostBasis4 = model.getCostBasis(portfolioName, "2022-10-07");
    assertEquals(this.investmentAmount + this.investmentCommission, actualCostBasis1, 0.0);
    assertEquals(Float.toString(2005.6f), Float.toString(actualCostBasis2));
    assertEquals(Float.toString(2005.6f), Float.toString(actualCostBasis3));
    assertEquals(Float.toString(4011.2f), Float.toString(actualCostBasis4));
  }

  @Test
  public void testWithInvalidArguments() {
    HashMap<String, Float> stocks = new HashMap<>();
    this.investmentAmount = 2000;
    this.investmentCommission = 5.6f;

    try {
      this.dca = new DollarCostAveraging<>(null, this.investmentAmount,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
              stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio required to apply strategy!", e.getMessage());
    }

    try {
      this.dca = new DollarCostAveraging<>("", this.investmentAmount,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
              stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio required to apply strategy!", e.getMessage());
    }

    try {
      this.dca = new DollarCostAveraging<>(this.portfolioName, this.investmentAmount,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
              stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No stocks!", e.getMessage());
    }

    try {
      this.dca = new DollarCostAveraging<>(this.portfolioName, 0,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
              stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Investment amount should be a positive non-zero value!", e.getMessage());
    }

    try {
      this.dca = new DollarCostAveraging<>(this.portfolioName, -1000,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
              stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Investment amount should be a positive non-zero value!", e.getMessage());
    }

    stocks.put("META", 40f);
    stocks.put("AAPL", 20f);
    stocks.put("NFLX", 30f);

    try {
      this.dca = new DollarCostAveraging<>(this.portfolioName, this.investmentAmount,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
              stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Weights don't add upto 100% !", e.getMessage());
    }

    stocks.put("GOOG", 10f);

    try {
      this.dca = new DollarCostAveraging<>(this.portfolioName, this.investmentAmount,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 30,
              stocks, -5);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Commission cannot be negative!", e.getMessage());
    }

    try {
      this.dca = new DollarCostAveraging<>(this.portfolioName, this.investmentAmount,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), 0,
              stocks, -5);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid interval provided!", e.getMessage());
    }

    try {
      this.dca = new DollarCostAveraging<>(this.portfolioName, this.investmentAmount,
              LocalDate.parse("2022-09-07"), LocalDate.parse("2022-11-20"), -4,
              stocks, -5);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid interval provided!", e.getMessage());
    }
  }

  @Test
  public void testHolidayDates() {
    HashMap<String, Float> stocks = new HashMap<>();
    stocks.put("AAPL", 40f);
    stocks.put("NFLX", 40f);
    stocks.put("GOOG", 20f);

    PortfolioOperation<Void> dca = new DollarCostAveraging<>(this.portfolioName, 2000,
            LocalDate.parse("2018-01-01"), LocalDate.parse("2020-12-15"), 30,
            stocks, 5.6f);
    dca.operate(this.model);
    this.model.persist();
  }
}
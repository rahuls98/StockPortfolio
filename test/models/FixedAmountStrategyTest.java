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

  @Test
  public void testWithInvalidArguments() {
    HashMap<String, Float> stocks = new HashMap<>();
    this.investmentAmount = 2000;
    this.investmentCommission = 5.6f;

    try {
      this.fas = new FixedAmountStrategy<>(null, this.investmentAmount, LocalDate.parse(
              "2022-11-10"), stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio required to apply strategy!", e.getMessage());
    }

    try {
      this.fas = new FixedAmountStrategy<>("", this.investmentAmount, LocalDate.parse(
              "2022-11-10"), stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio required to apply strategy!", e.getMessage());
    }

    try {
      this.fas = new FixedAmountStrategy<>(portfolioName, this.investmentAmount, LocalDate.parse(
              "2022-11-10"), stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("No stocks!", e.getMessage());
    }

    try {
      this.fas = new FixedAmountStrategy<>(portfolioName, 0, LocalDate.parse(
              "2022-11-10"), stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Investment amount should be a positive non-zero value!", e.getMessage());
    }

    try {
      this.fas = new FixedAmountStrategy<>(portfolioName, -1000, LocalDate.parse(
              "2022-11-10"), stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Investment amount should be a positive non-zero value!", e.getMessage());
    }

    stocks.put("META", 40f);
    stocks.put("AAPL", 20f);
    stocks.put("NFLX", 30f);

    try {
      this.fas = new FixedAmountStrategy<>(portfolioName, this.investmentAmount, LocalDate.parse(
              "2022-11-10"), stocks, this.investmentCommission);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Weights don't add upto 100% !", e.getMessage());
    }

    stocks.put("GOOG", 10f);

    try {
      this.fas = new FixedAmountStrategy<>(portfolioName, this.investmentAmount, LocalDate.parse(
              "2022-11-10"), stocks, -5);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Commission cannot be negative!", e.getMessage());
    }
  }
}